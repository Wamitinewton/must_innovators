package com.newton.admin.data.repository

import com.newton.admin.data.mappers.EventMapper.toEventDaoEntity
import com.newton.admin.data.mappers.EventMapper.toEventData
import com.newton.admin.data.remote.AdminApi
import com.newton.admin.domain.models.AddCommunityRequest
import com.newton.admin.domain.models.NewsLetter
import com.newton.admin.domain.models.NewsLetterResponse
import com.newton.admin.domain.repository.AdminRepository
import com.newton.core.domain.models.admin_models.EventsRsvpResponse
import com.newton.core.domain.models.event_models.AddEventRequest
import com.newton.core.domain.models.event_models.EventsData
import com.newton.core.utils.Resource
import com.newton.database.dao.EventDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val eventApi: AdminApi,
    private val eventDao: EventDao
): AdminRepository {
    override suspend fun addEvent(event: AddEventRequest): Flow<Resource<EventsData>> = flow{
        emit(Resource.Loading(true))
        try {
            val response = eventApi.createEvent(
                name = event.name,
                category = event.category,
                description = event.description,
                image = event.image,
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

    override suspend fun addCommunity(community: AddCommunityRequest): Flow<Resource<String>> = flow{
        emit(Resource.Loading(true))
        val response = eventApi.addCommunity(community)
//        if (response.status=="success"){
//            emit(Resource.Success(data = response.data.toEventData()))
//            eventDao.insertEvent(response.data.toEventDaoEntity())
//        }else{
//            emit(Resource.Error(response.message))
//        }
    }

    override suspend fun addNewsLetter(newsLetter: NewsLetter): Flow<Resource<NewsLetterResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRsvpsData(): Flow<Resource<EventsRsvpResponse>> {
        TODO("Not yet implemented")
    }
}