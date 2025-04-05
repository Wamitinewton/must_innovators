package com.newton.auth.authInterceptor

object HeadersManager {
    const val TAG = "AuthInterceptor"
    const val AUTHORIZATION_HEADER = "Authorization"
    const val BEARER_PREFIX = "Bearer"
    const val REFRESH_HEADER = "x-refresh"
    const val NEW_ACCESS_TOKEN_HEADER = "x-access-token"
}
