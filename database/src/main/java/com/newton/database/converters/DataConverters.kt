package com.newton.database.converters

import androidx.room.*
import com.google.gson.*
import com.google.gson.reflect.*
import com.newton.core.domain.models.authModels.*
import com.newton.database.entities.*

class DataConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<ClubSocialMediaEntity> {
        val type = object : TypeToken<List<ClubSocialMediaEntity>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list: List<ClubSocialMediaEntity>): String = gson.toJson(list)

    @TypeConverter
    fun fromStringList(value: List<String>?): String? = value?.let { gson.toJson(it) }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromSocialMedia(value: SocialMedia?): String? = value?.let { gson.toJson(it) }

    @TypeConverter
    fun toSocialMedia(value: String?): SocialMedia? {
        return value?.let {
            gson.fromJson(it, SocialMedia::class.java)
        }
    }

    @TypeConverter
    fun fromProjectList(value: List<Project>?): String? = value?.let { gson.toJson(it) }

    @TypeConverter
    fun toProjectList(value: String?): List<Project>? {
        return value?.let {
            val type = object : TypeToken<List<Project>>() {}.type
            gson.fromJson(it, type)
        }
    }
}
