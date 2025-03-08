package com.newton.admin.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.newton.admin.data.mappers.EventMapper.toEventDaoEntity
import com.newton.admin.data.mappers.EventMapper.toEventData
import com.newton.admin.data.remote.AdminApi
import com.newton.admin.domain.models.AddCommunityRequest
import com.newton.admin.domain.models.NewsLetter
import com.newton.admin.domain.models.NewsLetterResponse
import com.newton.admin.domain.repository.AdminRepository
import com.newton.core.domain.models.ApiResponse
import com.newton.core.domain.models.PaginationResponse
import com.newton.core.domain.models.admin_models.CommunityData
import com.newton.core.domain.models.admin_models.EventRegistrationData
import com.newton.core.domain.models.admin_models.FeedbackData
import com.newton.core.domain.models.event_models.AddEventRequest
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.utils.Resource
import com.newton.database.dao.EventDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
                name = event.name.toRequestBody("text/plain".toMediaTypeOrNull()),
                category = event.category.toRequestBody("text/plain".toMediaTypeOrNull()),
                description = event.description.toRequestBody("text/plain".toMediaTypeOrNull()),
                image = imagePart,
                date = event.date.toRequestBody("text/plain".toMediaTypeOrNull()),
                location = event.location.toRequestBody("text/plain".toMediaTypeOrNull()),
                organizer = event.organizer.toRequestBody("text/plain".toMediaTypeOrNull()),
                contactEmail = event.contactEmail.toRequestBody("text/plain".toMediaTypeOrNull()),
                title = event.title.toRequestBody("text/plain".toMediaTypeOrNull()),
                isVirtual = if(event.isVirtual)"True".toRequestBody("text/plain".toMediaTypeOrNull()) else "False".toRequestBody("text/plain".toMediaTypeOrNull())
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

    override suspend fun addCommunity(community: AddCommunityRequest): Flow<Resource<ApiResponse<CommunityData>>> = flow{
        emit(Resource.Loading(true))
        try {
            val response = adminApi.addCommunity(community)
            emit(Resource.Success(data = response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message?:"Error occurred while adding product"))
        }finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateCommunity(community: AddCommunityRequest): Flow<Resource<CommunityData>> = flow{
        emit(Resource.Loading(true))
//        val response =  adminApi.updateCommunity(community)
    }

    override suspend fun sendNewsLetter(newsLetter: NewsLetter): Flow<Resource<NewsLetterResponse>> =flow{
        emit(Resource.Loading(true))
        try {
            val response:NewsLetterResponse = adminApi.sendNewsLetter(newsLetter)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?:"Error occurred when sending newsletter"))
        }finally {
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getRsvpsData(eventId: Int): Flow<Resource<ApiResponse<PaginationResponse<EventRegistrationData>>>> = flow{
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

    override suspend fun getEventFeedbackBYId(eventId: Int): Flow<Resource<ApiResponse<PaginationResponse<FeedbackData>>>> {
        TODO("Not yet implemented")
    }
}