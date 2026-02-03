package com.sergokuzneczow.network.impl.retrofit

import com.sergokuzneczow.network.impl.retrofit.api.PostsApi
import com.sergokuzneczow.network.impl.retrofit.api.AuthResponseApi
import com.sergokuzneczow.network.impl.retrofit.api.PhoneMaskApi
import jakarta.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal class RetrofitHandler @Inject constructor() {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.NONE })
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(RetrofitSettings.BASE_URL + RetrofitSettings.API)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _phoneMaskApi: PhoneMaskApi by lazy { retrofit.create(PhoneMaskApi::class.java) }

    private val _authResponseApi: AuthResponseApi by lazy { retrofit.create(AuthResponseApi::class.java) }

    private val _postsApi: PostsApi by lazy { retrofit.create(PostsApi::class.java) }

    internal val phoneMaskApi: PhoneMaskApi
        get() = _phoneMaskApi

    internal val authResponseApi: AuthResponseApi
        get() = _authResponseApi

    internal val postsApi: PostsApi
        get() = _postsApi
}