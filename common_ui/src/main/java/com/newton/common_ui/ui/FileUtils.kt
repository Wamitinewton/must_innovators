package com.newton.common_ui.ui

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileNotFoundException

private const val DIRECTORY = "Innovators"

fun Context.saveImageUriToPublicDirectory(uri: Uri): Result<String> = runCatching {
    val fileName = "image-oakane${System.currentTimeMillis()}.jpg"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/$DIRECTORY")
        }
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            ?: throw FileNotFoundException("Failed to create MediaStore entry")
        contentResolver.openOutputStream(imageUri)?.use { outputStream ->
            contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        fileName
    } else {
        val picturesDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), DIRECTORY)
        if (!picturesDir.exists()) {
            picturesDir.mkdirs()
        }
        val file = File(picturesDir, fileName)
        contentResolver.openInputStream(uri)?.use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        file.absolutePath
    }
}
