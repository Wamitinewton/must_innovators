package com.newton.database.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newton.auth.domain.models.get_user.GetUserData

class DataConverters {
    @TypeConverter
    fun fromUserData(userData: GetUserData?): String? {
        return Gson().toJson(userData)
    }

    @TypeConverter
    fun toUserData(userDataString: String?): GetUserData? {
        return Gson().fromJson(userDataString, object : TypeToken<GetUserData>() {}.type)
    }

//    @TypeConverters
//    fun toListEntity(value: List)
}