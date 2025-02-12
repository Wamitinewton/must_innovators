package com.newton.database.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newton.auth.domain.models.sign_up.UserDataResponse

class DataConverters {
    @TypeConverter
    fun fromUserData(userData: UserDataResponse): String? {
        return Gson().toJson(userData)
    }

    @TypeConverter
    fun toUserData(userDataString: String?): UserDataResponse? {
        return Gson().fromJson(userDataString, object : TypeToken<UserDataResponse>() {}.type)
    }

//    @TypeConverters
//    fun toListEntity(value: List)
}