package com.sergokuzneczow.network.impl.models

import com.sergokuzneczow.model.Post
import com.sergokuzneczow.network.impl.retrofit.RetrofitSettings
import kotlin.time.Instant

internal data class PostRemoteModel(
    val id: String,
    val title: String,
    val text: String,
    val image: String,
    val sort: Int,
    val date: String,
)

internal val List<PostRemoteModel>.asListPosts: List<Post>
    get() = this.map {
        Post(
            id = it.id,
            title = it.title,
            text = it.text,
            imageUrl = RetrofitSettings.BASE_URL + it.image,
            sort = it.sort,
            date = Instant.parse(it.date).toEpochMilliseconds(),
        )
    }