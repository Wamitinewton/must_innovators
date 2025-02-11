package com.newton.database.converters

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

class DataConverters {
    @TypeConverter
    fun toStringImageList(value: List<String>): String {
        return Gson().toJson(value)
    }

//    @TypeConverters
//    fun toListEntity(value: List)
}