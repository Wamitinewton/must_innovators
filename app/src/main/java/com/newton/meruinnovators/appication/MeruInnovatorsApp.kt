package com.newton.meruinnovators.appication

import android.app.Application
import coil3.ImageLoader
//import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MeruInnovatorsApp: Application() {

    @Inject
    lateinit var imageLoader: ImageLoader

    fun newImageLoader(): ImageLoader = imageLoader
}