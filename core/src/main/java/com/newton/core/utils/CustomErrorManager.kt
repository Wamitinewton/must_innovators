package com.newton.core.utils

import retrofit2.HttpException
import java.io.IOException

sealed class CustomErrorManager(val message: String) {
    data object Network: CustomErrorManager("Please check your internet connection and try again")
    data object Server: CustomErrorManager("Unable to connect to server. Please try again later")
    data object Timeout: CustomErrorManager("Request timed out. Please try again")
    data object NotFound: CustomErrorManager("The requested resource could not be found")
    data object Unauthorized: CustomErrorManager("You are not authorized to make this request")
    class Unknown(message: String): CustomErrorManager(message)

    companion object {
        fun from(throwable: Throwable): CustomErrorManager {
            return when (throwable) {
                is IOException -> Network
                is HttpException -> {
                    when(throwable.code()) {
                        401 -> Unauthorized
                        404 -> NotFound
                        in 500..599 -> Server
                        else -> Unknown("An unexpected error occurred")
                    }
                }
                else -> Unknown(throwable.message ?: "An unexpected error occurred")
            }
        }
    }
}

fun CustomErrorManager.toException(): Exception = Exception(this.message)