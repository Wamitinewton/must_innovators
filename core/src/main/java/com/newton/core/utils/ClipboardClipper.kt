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
package com.newton.core.utils

import android.content.*
import android.widget.*

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
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
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
