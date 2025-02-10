package com.newton.auth.data.token_holder

import com.newton.auth.data.data_store.SessionManager

object AuthTokenHolder {
    var accessToken: String? = null
    var refreshToken: String? = null

    fun initializeTokens(sessionManager: SessionManager) {
        accessToken = sessionManager.fetchAccessToken()
        refreshToken = sessionManager.fetchRefreshToken()
    }
}