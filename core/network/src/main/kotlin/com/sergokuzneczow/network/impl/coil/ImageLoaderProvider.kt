package com.sergokuzneczow.network.impl.coil

import android.content.Context
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import okhttp3.OkHttpClient

@Singleton
internal class ImageLoaderProvider @Inject constructor(@ApplicationContext context: Context) {

    private val okHttpClient = OkHttpClient()

    val imageLoader: ImageLoader = ImageLoader.Builder(context)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }.components {
            add(OkHttpNetworkFetcherFactory(callFactory = { okHttpClient }))
        }.build()
}