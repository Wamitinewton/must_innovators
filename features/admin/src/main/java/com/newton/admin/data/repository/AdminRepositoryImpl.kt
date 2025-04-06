/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.admin.data.repository

import com.newton.admin.data.mappers.AttendeeMapper.toAttendeeList
import com.newton.admin.data.mappers.EventMapper.toEventDaoEntity
import com.newton.admin.data.mappers.EventMapper.toEventData
import com.newton.admin.data.mappers.UserFeedbackMapper.toDomain
import com.newton.admin.data.mappers.UserFeedbackMapper.toFeedbackData
import com.newton.admin.data.mappers.UserFeedbackMapper.toUserFeedbackListEntity
import com.newton.commonUi.ui.*
import com.newton.core.enums.*
import com.newton.database.dao.*
import com.newton.database.mappers.*
import com.newton.network.*
import com.newton.network.data.remote.*
import com.newton.network.domain.models.admin.*
import com.newton.network.domain.models.adminModels.*
import com.newton.network.domain.models.homeModels.*
import com.newton.network.domain.repositories.*
import kotlinx.coroutines.flow.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.*
import timber.log.*
import java.io.*
import javax.inject.*

class AdminRepositoryImpl
@Inject
constructor(
    private val adminApi: AdminApi,
    private val eventDao: EventDao,
    private val userFeedbackDao: UserFeedbackDao,
    private val eventsFeedbackDao: EventsFeedbackDao,
    private val partnersDao: PartnersDao
) : AdminRepository {
    override suspend fun addEvent(event: AddEventRequest): Flow<Resource<EventsData>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val requestFile = event.image.asRequestBody("image/*".toMediaTypeOrNull())
                val imagePart =
                    MultipartBody.Part.createFormData("image", event.image.name, requestFile)

                val params =
                    mapOf(
                        "name" to event.name.toRequestBody(),
                        "category" to event.category.toRequestBody(),
                        "description" to event.description.toRequestBody(),
                        "date" to event.date.toRequestBody(),
                        "location" to event.location.toRequestBody(),
                        "organizer" to event.organizer.toRequestBody(),
                        "contact_email" to event.contactEmail.toRequestBody(),
                        "title" to event.title.toRequestBody(),
                        "is_virtual" to if (event.isVirtual) "True".toCustomRequestBody() else "False".toCustomRequestBody()
                    )

                val response = adminApi.createEvent(params, imagePart)
                if (response.status == "success") {
                    emit(Resource.Success(data = response.data.toEventData()))
                    eventDao.insertEvent(response.data.toEventDaoEntity())
                } else {
                    emit(Resource.Error(response.message))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error when adding event"))
            } finally {
                emit(Resource.Loading(false))
            }
        }

    override suspend fun addCommunity(community: AddCommunityRequest): Flow<Resource<ApiResponse<CommunityData>>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response = adminApi.addCommunity(community)
                emit(Resource.Success(data = response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Error occurred while adding product"))
            } finally {
                emit(Resource.Loading(false))
            }
        }

    override suspend fun updateCommunity(
        communityId: Int,
        community: UpdateCommunityRequest
    ): Flow<Resource<CommunityData>> =
        safeApiCall {
            adminApi.updateCommunity(communityId, community)
        }

    override suspend fun updateEvent(
        event: UpdateEventRequest,
        eventId: Int
    ): Flow<Resource<EventsData>> =
        safeApiCall {
            val response = adminApi.updateEvent(eventId, event)
            if (response.message == "success") {
                response.data
            } else {
                throw Exception("Failed to fetch feedbacks")
            }
        }

    override suspend fun sendNewsLetter(newsLetter: NewsLetter): Flow<Resource<NewsLetterResponse>> =
        safeApiCall {
            adminApi.sendNewsLetter(newsLetter)
        }

    override suspend fun getEventFeedbackBYId(
        eventId: Int,
        isRefresh: Boolean
    ): Flow<Resource<List<EventsFeedback>>> =
        safeApiCall {
            val response = adminApi.getEventsFeedback(eventId)

            if (response.status == "success" && response.data.results.isNotEmpty()) {
                response.data.results
            } else {
                throw Exception("Failed to fetch feedbacks")
            }
        }

    override suspend fun getAllFeedbacks(
        isRefresh: Boolean,
        category: String?,
        ordering: String?,
        status: FeedbackStatus?,
        search: String?
    ): Flow<Resource<List<FeedbackData>>> =
        flow {
            emit(Resource.Loading(true))
            try {
                if (isRefresh) {
                    getRemoteFeedbacks(category, ordering, status?.name, search)?.let {
                        emit(it)
                    }
                } else {
                    val localFeedbacks = userFeedbackDao.getAllUserFeedbacks()
                    localFeedbacks.collectLatest { feedback ->
                        if (feedback.isNotEmpty()) {
                            emit(Resource.Success(feedback.map { it.toDomain() }))
                        } else {
                            getRemoteFeedbacks(
                                category,
                                ordering,
                                status?.name,
                                search
                            )?.let { emit(it) }
                        }
                    }
                }
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        e.message ?: "Unknown error occurred while getting all users feedback"
                    )
                )
            } finally {
                emit(Resource.Loading(false))
            }
        }

    override suspend fun getAllUsers(isRefresh: Boolean): Flow<Resource<List<UserData>>> =
        flow {
            emit(Resource.Loading(true))
            try {
                if (isRefresh) {
                    val response = adminApi.getAllUsers()
                    emit(Resource.Success(response.data))
                } else {
                    val response = adminApi.getAllUsers()
                    emit(Resource.Success(response.data))
                }
            } catch (e: HttpException) {
                emit(Resource.Error("An HTTP error occurred: ${e.message ?: "Unknown HTTP error"}"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error("An unexpected error occurred: ${e.message ?: "Unknown error"}"))
            } finally {
                emit(Resource.Loading(false))
            }
        }

    override suspend fun getRegistrationList(eventId: Int): Flow<Resource<List<Attendee>>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response = adminApi.getAttendeeData(eventId)
                if (response.status == "success") {
                    emit(Resource.Success(response.data.results.toAttendeeList()))
                } else {
                    emit(Resource.Error(response.message))
                }
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        e.message ?: "Unknown error occurred while getting rsvps data"
                    )
                )
            } finally {
                emit(Resource.Loading(false))
            }
        }

    override suspend fun getListOfEvents(): Flow<Resource<List<EventsData>>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val events = adminApi.getAllEventsList()
                if (events.status == "success") {
                    emit(Resource.Success(events.data.results))
                    saveListOfEvents(events.data.results)
                } else {
                    val localEvents = eventDao.getListOfEvents()
                    emit(Resource.Success(localEvents.toEventDataList()))
                }
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        e.message ?: "Unknown error occurred while getting local events data"
                    )
                )
            } finally {
                emit(Resource.Loading(false))
            }
        }

    override suspend fun addPartner(partners: AddPartnerRequest): Flow<Resource<PartnersData>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val params =
                    mapOf(
                        "name" to partners.name.toCustomRequestBody(),
                        "type" to partners.type.toCustomRequestBody(),
                        "description" to partners.description.toCustomRequestBody(),
                        "web_url" to partners.webUrl.toCustomRequestBody(),
                        "contact_email" to partners.contactEmail.toCustomRequestBody(),
                        "contact_person" to partners.contactPerson.toCustomRequestBody(),
                        "linked_in" to partners.linkedIn.toCustomRequestBody(),
                        "twitter" to partners.twitter.toCustomRequestBody(),
                        "start_date" to partners.startDate.toCustomRequestBody(),
                        "ongoing" to partners.ongoing.toString().toCustomRequestBody(),
                        "status" to partners.status.toCustomRequestBody(),
                        "scope" to partners.scope.toCustomRequestBody(),
                        "benefits" to partners.benefits.toCustomRequestBody(),
                        "events_supported" to partners.eventsSupported.toCustomRequestBody(),
                        "resources" to partners.resources.toCustomRequestBody(),
                        "achievements" to partners.achievements.toCustomRequestBody(),
                        "target_audience" to partners.targetAudience.toCustomRequestBody()
                    )
                val endDateRequestBody = partners.endDate?.toCustomRequestBody()
                val filteredParams =
                    if (endDateRequestBody != null) {
                        params + ("end_date" to endDateRequestBody)
                    } else {
                        params
                    }

                val requestFile = partners.logo.asRequestBody("image/*".toMediaTypeOrNull())
                val file = partners.logo
                val fileName =
                    if (file.name.contains(".")) {
                        file.name
                    } else {
                        "${file.name}.jpg"
                    }
                val imagePart =
                    MultipartBody.Part.createFormData("logo_field", fileName, requestFile)

                val response = adminApi.addPartner(filteredParams, imagePart)
                if (response.status == "success") {
                    emit(Resource.Success(response.data))
                    partnersDao.insertPartner(response.data.toPartnerEntity())
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown Error occurred"))
            } finally {
                emit(Resource.Loading(false))
            }
        }

    override suspend fun updateExecutive(executive: ExecutiveRequest): Flow<Resource<ExecutiveResponse>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response = adminApi.addExecutive(executive)
                if (response.status == "success") {
                    response.data
                } else {
                    emit(Resource.Error("Failed to fetch users: ${response.message}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error occurred"))
            } finally {
                emit(Resource.Loading(false))
            }
        }

    override suspend fun updateClub(clubRequest: Club): Flow<Resource<Club>> =
        flow {
            try {
                val response = adminApi.updateClub(clubRequest)
                if (response.status == "success") {
                    response.data
                } else {
                    emit(Resource.Error("Error occurred when updating club"))
                    throw Exception(response.message)
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error occurred"))
            }
        }

    private suspend fun getRemoteFeedbacks(
        category: String?,
        ordering: String?,
        status: String?,
        search: String?
    ): Resource<List<FeedbackData>>? {
        return try {
            val response = adminApi.getUserFeedbacks(category, ordering, search, status)

            if (response.results.isNotEmpty()) {
                val feedbacks = response.results.toFeedbackData()
                saveFeedbacks(feedbacks)
                Resource.Success(data = feedbacks)
            } else {
                Resource.Error("There is no feedbacks at the moment try again later")
            }
        } catch (e: HttpException) {
            Resource.Error("An HTTP error occurred: ${e.message ?: "Unknown HTTP error"}")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection.")
        } catch (e: Exception) {
            Resource.Error("An unexpected error occurred: ${e.message ?: "Unknown error"}")
        }
    }

    private suspend fun getRemoteEventsFeedbacks(eventId: Int): Resource<List<EventsFeedback>>? {
        return try {
            val response = adminApi.getEventsFeedback(eventId)

            if (response.status == "success" && response.data.results.isNotEmpty()) {
                val feedbacks = response.data.results
                saveEventsFeedbacks(feedbacks)
                Resource.Success(data = feedbacks)
            } else {
                Resource.Error("Failed to fetch feedbacks: ${response.message}")
            }
        } catch (e: HttpException) {
            Resource.Error("An HTTP error occurred: ${e.message ?: "Unknown HTTP error"}")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection.")
        } catch (e: Exception) {
            Resource.Error("An unexpected error occurred: ${e.message ?: "Unknown error"}")
        }
    }

    private suspend fun saveFeedbacks(feedbacks: List<FeedbackData>) {
        try {
            userFeedbackDao.deleteAllFeedbacks()
            userFeedbackDao.insertFeedbacks(feedbacks.toUserFeedbackListEntity())
        } catch (e: Exception) {
            Timber.tag("Error saving feedbacks")
                .e(e.message ?: "Unknown error saving feedbacks")
        }
    }

    private suspend fun saveEventsFeedbacks(feedbacks: List<EventsFeedback>) {
        try {
            eventsFeedbackDao.deleteAllEventsFeedbacks()
            eventsFeedbackDao.insertEventsFeedback(feedbacks.toEventsFeedbackList())
        } catch (e: Exception) {
            Timber.tag("Error saving events feedbacks")
                .e(e.message ?: "Unknown error saving feedbacks")
        }
    }

    private suspend fun saveListOfEvents(events: List<EventsData>) {
        try {
            eventDao.clearEvents()
            eventDao.insertEvents(events.toEventsEntity())
        } catch (e: Exception) {
            Timber.tag("Error saving events feedbacks")
                .e(e.message ?: "Unknown error saving feedbacks")
        }
    }
}
