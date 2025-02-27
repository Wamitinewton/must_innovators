package com.newton.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newton.core.domain.models.auth_models.GetUserData
import com.newton.core.domain.models.event_models.EventsData

class DataConverters {
    @TypeConverter
    fun fromUserData(userData: GetUserData?): String? {
        return Gson().toJson(userData)
    }

    @TypeConverter
    fun toUserData(userDataString: String?): GetUserData? {
        return Gson().fromJson(userDataString, object : TypeToken<GetUserData>() {}.type)
    }

    @TypeConverter
    fun fromEventsData(eventsData: EventsData?): String? {
        return Gson().toJson(eventsData)
    }

    @TypeConverter
    fun toEventsData(eventsDataString: String?): EventsData? {
        return Gson().fromJson(eventsDataString, object : TypeToken<EventsData>() {}.type)
    }

}