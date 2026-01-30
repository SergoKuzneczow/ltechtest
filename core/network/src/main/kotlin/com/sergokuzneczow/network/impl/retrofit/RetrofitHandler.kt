package com.sergokuzneczow.network.impl.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RetrofitHandler {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(RetrofitSettings.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _phoneMaskApi: PhoneMaskApi by lazy { retrofit.create(PhoneMaskApi::class.java) }

    internal val phoneMaskApi: PhoneMaskApi = _phoneMaskApi
}