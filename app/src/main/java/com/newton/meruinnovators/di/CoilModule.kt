package com.newton.meruinnovators.di

import android.content.*
import coil3.*
import coil3.disk.*
import coil3.memory.*
import coil3.request.*
import coil3.util.*
import com.newton.meruinnovators.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {
    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
//            .respectCacheHeaders(false)
            .crossfade(true)
            .crossfade(300)
            .allowRgb565(true)
            .logger(if (BuildConfig.DEBUG) DebugLogger() else null)
            .build()
    }
}
