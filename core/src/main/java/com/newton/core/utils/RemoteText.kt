package com.newton.core.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class RemoteText {

    data class RemoteString(val message: String): RemoteText()
    class LocalString(@StringRes val res: Int, vararg val args: Any): RemoteText()

    data object Idle: RemoteText()

    fun getString(context: Context): String {
        return when(this) {
            is RemoteString -> {
                message
            }
            is Idle -> ""
            is LocalString -> {
                context.getString(res, *args)
            }
        }
    }

    @Composable
    fun getString(): String {
        return when (this) {
            is RemoteString -> {
                message
            }
            is LocalString -> {
                stringResource(res, *args)
            }
            Idle -> ""
        }

    }
}