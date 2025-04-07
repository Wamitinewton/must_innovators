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
import androidx.core.content.*
import java.io.*

class ShareManager(private val context: Context) {
    fun sharePdfFile(pdfFile: File) {
        val uri =
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                pdfFile
            )

        val intent =
            Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

        val chooserIntent =
            Intent.createChooser(
                intent,
                "Share Ticket"
            )

        context.startActivity(chooserIntent)
    }
}
