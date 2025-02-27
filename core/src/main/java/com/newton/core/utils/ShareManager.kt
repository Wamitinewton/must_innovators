package com.newton.core.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

class ShareManager(private val context: Context) {
    fun sharePdfFile(pdfFile: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            pdfFile
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooserIntent = Intent.createChooser(
            intent,
            "Share Ticket"
        )

        context.startActivity(chooserIntent)
    }
}