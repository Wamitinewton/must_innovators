package com.newton.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newton.core.domain.models.auth_models.Project
import com.newton.core.domain.models.auth_models.SocialMedia

@Entity(tableName = "user")
data class UserEntity(
    val course: String? = null,
    val email: String,
    val firstName: String,
    val lastName: String,
    val userName: String,
    val registrationNo: String? = null,
    val bio: String? = null,
    val techStacks: List<String>? = null,
    val socialMedia: SocialMedia? = null,
    val photo: String? = null,
    val yearOfStudy: Int? = null,
    val graduationYear: Int? = null,
    val projects: List<Project>? = null,
    val skills: List<String>? = null,
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1
)

