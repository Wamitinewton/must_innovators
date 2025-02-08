package com.newton.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class DataConverters {
    @TypeConverter
    fun toStringImageList(value: List<String>): String {
        return Gson().toJson(value)
    }
}