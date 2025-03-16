package com.newton.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.newton.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user")
    suspend fun getUser(): UserEntity?

    @Query("DELETE FROM user")
    suspend fun deleteUser()

    @Query("SELECT * FROM user LIMIT 1")
    fun observeUser(): Flow<UserEntity?>

    @Query("UPDATE user SET firstName = :firstName, lastName = :lastName WHERE id = :userId")
    suspend fun updateNames(userId: Int, firstName: String, lastName: String)

    @Query("UPDATE user SET email = :email WHERE id = :userId")
    suspend fun updateEmail(userId: Int, email: String)

    @Query("UPDATE user SET course = :course WHERE id = :userId")
    suspend fun updateCourse(userId: Int, course: String?)

    @Query("UPDATE user SET registrationNo = :registrationNo WHERE id = :userId")
    suspend fun updateRegistrationNo(userId: Int, registrationNo: String?)

    @Query("UPDATE user SET bio = :bio WHERE id = :userId")
    suspend fun updateBio(userId: Int, bio: String?)

    @Query("UPDATE user SET yearOfStudy = :yearOfStudy, graduationYear = :graduationYear WHERE id = :userId")
    suspend fun updateAcademicInfo(userId: Int, yearOfStudy: Int?, graduationYear: Int?)

    @Query("UPDATE user SET photo = :photo WHERE id = :userId")
    suspend fun updatePhoto(userId: Int, photo: String?)
}