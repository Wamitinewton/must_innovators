package com.newton.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import retrofit2.HttpException as RetrofitHttpException


fun <T> safeApiCall(
    apiCall: suspend () -> T
): Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading(true))
        val response = apiCall()
        emit(Resource.Success(data = response))
    } catch (e: RetrofitHttpException) {
        emit(Resource.Error(message = handleHttpError(e)))
    } catch (e: HttpException) {
        emit(Resource.Error(message = "An unexpected error occurred: ${e.message}"))
    } catch (e: IOException) {
        emit(Resource.Error(message = "Network error: Please check your internet connection"))
    } catch (e: Exception) {
        emit(Resource.Error(message = "An unexpected error occurred: ${e.message}"))
    } finally {
        emit(Resource.Loading(false))
    }
}

private fun handleHttpError(error: RetrofitHttpException, message: String? = null): String {
    return when (error.code()) {
        400 -> "Invalid request: Please check your input"
        401 -> "Unauthorized: Please log in again"
        403 -> "Access denied. ${message ?: ""}"
        404 -> "Resource not found"
        500, 502, 503 -> "Server error: Please try again later"
        else -> "Network error: ${error.message()}"
    }
}

