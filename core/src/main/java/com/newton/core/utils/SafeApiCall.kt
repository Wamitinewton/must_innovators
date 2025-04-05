package com.newton.core.utils

import kotlinx.coroutines.flow.*
import retrofit2.*
import java.io.*
import java.net.*

fun <T> safeApiCall(apiCall: suspend () -> T): Flow<Resource<T>> =
    flow {
        try {
            emit(Resource.Loading(true))
            val response = apiCall()
            emit(Resource.Success(data = response))
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error(message = "Request timed out. Please try again."))
        } catch (e: HttpException) {
            val errorMessage = handleError(e)
            emit(Resource.Error(message = errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Network error: Please check your internet connection"))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = "An unexpected error occurred: ${e.message ?: "Unknown error"}"
                )
            )
        } finally {
            emit(Resource.Loading(isLoading = false))
        }
    }

private fun handleError(
    error: HttpException,
    message: String? = null
): String {
    return when (val code = error.code()) {
        400 -> "Invalid request: Please check your input"
        401 -> "Unauthorized: Please log in again"
        403 -> "Access denied. ${message ?: ""}"
        404 -> "Resource not found"
        in 500..599 -> "Server error: Please try again later"
        else -> "Network error (Code: $code): ${error.message()}"
    }
}
