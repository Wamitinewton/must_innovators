/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.database.converters

import androidx.room.*
import com.google.gson.*
import com.google.gson.reflect.*
import com.newton.database.entities.*
import com.newton.network.domain.models.authModels.*

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
