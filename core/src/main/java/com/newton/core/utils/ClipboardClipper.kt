package com.newton.core.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

/**
 * Copies text to clipboard
 *
 * @param context Android context
 * @param text Text to copy to clipboard
 * @param label Label for the clipboard content
 * @param showToast Whether to show a toast message
 * @param toastMessage Custom toast message (defaults to "Copied to clipboard")
 * @return Boolean indicating whether copy was successful
 */

fun copyToClipboard(
    context: Context,
    text: String,
    label: String = "Copied Text",
    showToast: Boolean = true,
    toastMessage: String = "Copied to clipboard"
): Boolean {
    return try {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(label, text)
        clipboardManager.setPrimaryClip(clipData)

        if (showToast) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
        }
        true
    } catch (e: Exception) {
        if (showToast) {
            Toast.makeText(context, "Failed to copy to clipboard", Toast.LENGTH_LONG).show()
        }
        false
    }
}