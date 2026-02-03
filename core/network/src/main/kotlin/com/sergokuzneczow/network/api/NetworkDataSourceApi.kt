package com.sergokuzneczow.network.api

import android.graphics.Bitmap
import coil3.ImageLoader
import com.sergokuzneczow.model.AuthResponse
import com.sergokuzneczow.model.PhoneMask
import com.sergokuzneczow.model.Post

public interface NetworkDataSourceApi {

    public suspend fun getPhoneMasks(): PhoneMask

    public suspend fun getAuthResponse(phone: String, password: String): AuthResponse

    public suspend fun getPosts(): List<Post>

    public fun imageLoader(): ImageLoader
}