package com.newton.admin.data.repository

import android.util.Log
import com.newton.admin.data.mappers.EventMapper.toEventDaoEntity
import com.newton.admin.data.mappers.EventMapper.toEventData
import com.newton.admin.data.mappers.UserFeedbackMapper.toDomain
import com.newton.admin.data.mappers.UserFeedbackMapper.toUserFeedbackListEntity
import com.newton.admin.data.remote.AdminApi
import com.newton.core.domain.models.ApiResponse
import com.newton.core.domain.models.PaginationResponse
import com.newton.core.domain.models.admin_models.AddCommunityRequest
import com.newton.core.domain.models.admin.NewsLetter
import com.newton.core.domain.models.admin.NewsLetterResponse
import com.newton.core.domain.models.admin_models.AddEventRequest
import com.newton.core.domain.models.admin_models.CommunityData
import com.newton.core.domain.models.admin_models.EventRegistrationData
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.domain.models.admin_models.FeedbackData
import com.newton.core.domain.models.admin_models.UserData
import com.newton.core.domain.repositories.AdminRepository
import com.newton.core.utils.Resource
import com.newton.database.dao.EventDao
import com.newton.database.dao.UserFeedbackDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminApi: AdminApi,
    private val eventDao: EventDao,
    private val userFeedbackDao: UserFeedbackDao
) : AdminRepository {
    override suspend fun addEvent(event: AddEventRequest): Flow<Resource<EventsData>> = flow {
        emit(Resource.Loading(true))
        try {
            val requestFile = event.image.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart =
                MultipartBody.Part.createFormData("image", event.image.name, requestFile)


            val response = adminApi.createEvent(
                name = event.name.toRequestBody("text/plain".toMediaTypeOrNull()),
                category = event.category.toRequestBody("text/plain".toMediaTypeOrNull()),
                description = event.description.toRequestBody("text/plain".toMediaTypeOrNull()),
                image = imagePart,
                date = event.date.toRequestBody("text/plain".toMediaTypeOrNull()),
                location = event.location.toRequestBody("text/plain".toMediaTypeOrNull()),
                organizer = event.organizer.toRequestBody("text/plain".toMediaTypeOrNull()),
                contactEmail = event.contactEmail.toRequestBody("text/plain".toMediaTypeOrNull()),
                title = event.title.toRequestBody("text/plain".toMediaTypeOrNull()),
                isVirtual = if (event.isVirtual) "True".toRequestBody("text/plain".toMediaTypeOrNull()) else "False".toRequestBody(
                    "text/plain".toMediaTypeOrNull()
                )
            )
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

    override suspend fun updateCommunity(community: AddCommunityRequest): Flow<Resource<CommunityData>> =
        flow {
            emit(Resource.Loading(true))
//        val response =  adminApi.updateCommunity(community)
        }

    override suspend fun sendNewsLetter(newsLetter: NewsLetter): Flow<Resource<NewsLetterResponse>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response: NewsLetterResponse = adminApi.sendNewsLetter(newsLetter)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Error occurred when sending newsletter"))
            } finally {
                emit(Resource.Loading(false))
            }
        }

    override suspend fun getRsvpsData(eventId: Int): Flow<Resource<ApiResponse<PaginationResponse<EventRegistrationData>>>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response = adminApi.getRsvpsData(eventId)
                if (response.status == "success") {
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Error(response.message))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error occurred while getting rsvp data"))
            } finally {
                emit(Resource.Loading(false))
            }
        }

    override suspend fun getEventFeedbackBYId(eventId: Int): Flow<Resource<ApiResponse<FeedbackData>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFeedbacks(isRefresh: Boolean): Flow<Resource<List<FeedbackData>>> =
        flow {
            emit(Resource.Loading(true))
            try {
                if (isRefresh) {
                    getRemoteFeedbacks()?.let {
                        emit(it)
                    }

                } else {
                    val localFeedbacks = userFeedbackDao.getAllUserFeedbacks()
                    localFeedbacks.collectLatest { feedback ->
                        if (feedback.isNotEmpty()) {
                            emit(Resource.Success(feedback.map { it.toDomain() }))
                        } else {
                            getRemoteFeedbacks()?.let { emit(it) }
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

    override suspend fun getAllUsers(isRefresh: Boolean): Flow<Resource<List<UserData>>> = flow {
        emit(Resource.Loading(true))
        try {
            if (isRefresh) {
                val response = adminApi.getAllUsers()
                emit(Resource.Success(response.data))

            } else {
//                usersDao.getAllUsers()
                val response = adminApi.getAllUsers()
                emit(Resource.Success(response.data))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("An HTTP error occurred: ${e.message ?: "Unknown HTTP error"}"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.message ?: "Unknown error"}"))
        }
    }

    private suspend fun getRemoteFeedbacks(): Resource<List<FeedbackData>>? {
        return try {
            val response = adminApi.getUserFeedbacks()

            if (response.status == "success" && response.data.results.isNotEmpty()) {
                val feedbacks = response.data.results
                saveFeedbacks(feedbacks)
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
            Log.e("Error saving feedbacks", e.message ?: "Unknown error saving feedbacks")
        }
    }
}