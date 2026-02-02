package com.sergokuzneczow.network.impl.retrofit.api

import com.sergokuzneczow.network.impl.models.PostRemoteModel
import retrofit2.http.GET

internal interface PostsApi {

    @GET("v1/posts")
    suspend fun getPosts(): List<PostRemoteModel>
}