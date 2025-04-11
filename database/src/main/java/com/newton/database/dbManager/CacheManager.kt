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
package com.newton.database.dbManager

import android.content.*
import coil3.*
import dagger.hilt.android.qualifiers.*
import kotlinx.coroutines.*
import java.io.*
import javax.inject.*

/**
 * Helper class to manage cache operations throughout the app
 */
@Singleton
class CacheManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageLoader: ImageLoader
) {
    /**
     * Clears app cache files but preserves database data
     * @return The size of cleared cache in bytes
     */
    suspend fun clearAppCache(): Long = withContext(Dispatchers.IO) {
        var bytesCleared: Long = 0

        try {
            val cacheDir = context.cacheDir
            bytesCleared += deleteFilesRecursively(cacheDir)

            context.externalCacheDir?.let { externalCacheDir ->
                bytesCleared += deleteFilesRecursively(externalCacheDir)
            }

            bytesCleared += clearCoilCache()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        bytesCleared
    }

    /**
     * Clears Coil image cache (both memory and disk)
     * @return Size of cleared cache in bytes
     */
    private suspend fun clearCoilCache(): Long = withContext(Dispatchers.IO) {
        var bytesCleared: Long = 0

        try {
            imageLoader.memoryCache?.clear()

            val imageCacheDir = context.cacheDir.resolve("image_cache")
            if (imageCacheDir.exists()) {
                bytesCleared += getDirSize(imageCacheDir)
                imageLoader.diskCache?.clear()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        bytesCleared
    }

    /**
     * Utility method to recursively delete files in a directory
     * but skip database files
     */
    private fun deleteFilesRecursively(directory: File): Long {
        var deletedBytes: Long = 0

        if (directory.exists()) {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.isDirectory) {
                        // Skip database directories to preserve DB data
                        if (!file.name.contains("databases")) {
                            deletedBytes += deleteFilesRecursively(file)
                        }
                    } else {
                        // Skip database files
                        if (!file.name.endsWith(".db") &&
                            !file.name.endsWith(".db-shm") &&
                            !file.name.endsWith(".db-wal")
                        ) {
                            val fileSize = file.length()
                            if (file.delete()) {
                                deletedBytes += fileSize
                            }
                        }
                    }
                }
            }
        }
        return deletedBytes
    }


    /**
     * Get current cache size in bytes
     */
    suspend fun getCacheSize(): Long = withContext(Dispatchers.IO) {
        var size: Long = 0

        try {
            // Get size of main cache directory
            size += getDirSize(context.cacheDir)

            // Get size of external cache directory if available
            context.externalCacheDir?.let {
                size += getDirSize(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        size
    }

    private fun getDirSize(dir: File): Long {
        var size: Long = 0

        val files = dir.listFiles()
        if (files != null) {
            for (file in files) {
                size += if (file.isDirectory) {
                    getDirSize(file)
                } else {
                    file.length()
                }
            }
        }

        return size
    }

    /**
     * Format bytes into a human-readable string (KB, MB, etc.)
     */
    fun formatSize(bytes: Long): String {
        val kb = bytes / 1024.0
        val mb = kb / 1024.0

        return when {
            mb >= 1.0 -> String.format("%.2f MB", mb)
            kb >= 1.0 -> String.format("%.2f KB", kb)
            else -> "$bytes bytes"
        }
    }
}
