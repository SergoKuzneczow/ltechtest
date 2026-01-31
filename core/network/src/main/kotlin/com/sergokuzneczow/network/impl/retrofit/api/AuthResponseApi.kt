package com.sergokuzneczow.network.impl.retrofit.api

import com.sergokuzneczow.network.impl.models.AuthResponseRemoteModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface AuthResponseApi {

    @FormUrlEncoded
    @POST("v1/auth")
    suspend fun authenticate(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Response<AuthResponseRemoteModel>
}