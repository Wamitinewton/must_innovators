package com.newton.auth.data.tokenHolder

import com.newton.auth.data.dataStore.*

object AuthTokenHolder {
    var accessToken: String? = null
    var refreshToken: String? = null

    fun initializeTokens(sessionManager: SessionManager) {
        accessToken = sessionManager.fetchAccessToken()
        refreshToken = sessionManager.fetchRefreshToken()
    }
}
