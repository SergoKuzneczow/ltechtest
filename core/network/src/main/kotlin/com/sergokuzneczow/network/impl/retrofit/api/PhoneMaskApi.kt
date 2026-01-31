package com.sergokuzneczow.network.impl.retrofit.api

import com.sergokuzneczow.network.impl.models.PhoneMaskRemoteModel
import retrofit2.http.GET

internal interface PhoneMaskApi {

    @GET("v1/phone_masks")
    suspend fun getPhoneMask(): PhoneMaskRemoteModel
}