package com.newton.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newton.core.domain.models.auth_models.GetUserData

class DataConverters {
    @TypeConverter
    fun fromUserData(userData: GetUserData?): String? {
        return Gson().toJson(userData)
    }

    @TypeConverter
    fun toUserData(userDataString: String?): GetUserData? {
        return Gson().fromJson(userDataString, object : TypeToken<GetUserData>() {}.type)
    }


}