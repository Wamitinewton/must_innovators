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
package com.newton.auth.authInterceptor

import android.content.*
import com.newton.auth.authInterceptor.HeadersManager.AUTHORIZATION_HEADER
import com.newton.auth.authInterceptor.HeadersManager.BEARER_PREFIX
import com.newton.auth.authInterceptor.HeadersManager.NEW_ACCESS_TOKEN_HEADER
import com.newton.auth.authInterceptor.HeadersManager.REFRESH_HEADER
import com.newton.auth.authInterceptor.HeadersManager.TAG
import com.newton.auth.data.dataStore.*
import com.newton.auth.data.dataStore.DataStoreKeys.KEY_ACCESS_TOKEN
import com.newton.auth.data.dataStore.DataStoreKeys.KEY_REFRESH_TOKEN
import okhttp3.*
import timber.log.*

class AuthInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sessionManager.fetchAccessToken()?.let {
            requestBuilder.addHeader(AUTHORIZATION_HEADER, "$BEARER_PREFIX $it")
        }

        sessionManager.fetchRefreshToken()?.let {
            requestBuilder.addHeader(REFRESH_HEADER, it)
        }

        val response = chain.proceed(requestBuilder.build())

        getTokensFromResHeaders(response)

        return response
    }

    private fun getTokensFromResHeaders(response: Response) {
        var accessToken = response.headers[KEY_ACCESS_TOKEN]

        val refreshToken = response.headers[KEY_REFRESH_TOKEN]

        val newAccessToken = response.headers[NEW_ACCESS_TOKEN_HEADER]

        if (newAccessToken != null) accessToken = newAccessToken

        if (accessToken != null && refreshToken != null) {
            sessionManager.saveAccessToken(accessToken)

            sessionManager.saveRefreshToken(refreshToken)

            Timber.tag(TAG).d("access: $accessToken")
        } else {
            Timber.tag(TAG).d("Not token ${response.headers}")
        }
    }
}
