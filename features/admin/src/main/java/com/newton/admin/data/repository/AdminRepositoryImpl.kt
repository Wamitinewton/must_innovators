package com.newton.admin.data.repository

import com.newton.admin.data.mappers.EventMapper.toEventDaoEntity
import com.newton.admin.data.mappers.EventMapper.toEventData
import com.newton.admin.data.remote.AdminApi
import com.newton.core.domain.models.admin.AddCommunityRequest
import com.newton.core.domain.models.admin.NewsLetter
import com.newton.core.domain.models.admin.NewsLetterResponse
import com.newton.core.domain.repositories.AdminRepository
import com.newton.core.domain.models.admin_models.CommunityData
import com.newton.core.domain.models.admin_models.EventsRsvpResponse
import com.newton.core.domain.models.admin_models.AddEventRequest
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.utils.Resource
import com.newton.database.dao.EventDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminApi: AdminApi,
    private val eventDao: EventDao
): AdminRepository {
    override suspend fun addEvent(event: AddEventRequest): Flow<Resource<EventsData>> = flow{
        emit(Resource.Loading(true))
        try {
            val requestFile = event.image.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", event.image.name, requestFile)


            val response = adminApi.createEvent(
                name = event.name,
                category = event.category,
                description = event.description,
                image = imagePart,
                date = event.date,
                location = event.location,
                organizer = event.organizer,
                contactEmail = event.contactEmail,
                title = event.title,
                isVirtual = event.isVirtual
            )
            if (response.status=="success"){
                emit(Resource.Success(data = response.data.toEventData()))
                eventDao.insertEvent(response.data.toEventDaoEntity())
            }else{
                emit(Resource.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message?:"Unknown error when adding event"))
        }finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun addCommunity(community: AddCommunityRequest): Flow<Resource<CommunityData>> = flow{
        emit(Resource.Loading(true))
        try {
            val response = adminApi.addCommunity(community)
            emit(Resource.Success(data = response.data))
        } catch (e: Exception) {
            emit(Resource.Error(e.message?:"Error occurred while adding product"))
        }finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun sendNewsLetter(newsLetter: NewsLetter): Flow<Resource<NewsLetterResponse>> =flow{
        emit(Resource.Loading(true))
        try {
            val response: NewsLetterResponse = adminApi.sendNewsLetter(newsLetter)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?:"Error occurred when sending newsletter"))
        }finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getRsvpsData(eventId: Int): Flow<Resource<EventsRsvpResponse>> = flow{
        emit(Resource.Loading(true))
        try {
            val response = adminApi.getRsvpsData(eventId)
            if (response.status == "success"){
                emit(Resource.Success(response))
            }else{
                emit(Resource.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message?:"Unknown error occurred while getting rsvp data"))
        } finally {
            emit(Resource.Loading(false))
        }
    }
}