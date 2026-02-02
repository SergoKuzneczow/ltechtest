package com.sergokuzneczow.database.impl.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sergokuzneczow.model.Post

@Entity(tableName = "posts")
internal data class PostLocalModel(
    @PrimaryKey
    @ColumnInfo("key") val id: String,
    val title: String,
    val text: String,
    @ColumnInfo("image_uri") val imageUrl: String,
    val sort: Int,
    val date: Long,
)

internal val List<PostLocalModel>.asListPosts: List<Post>
    get() = this.map {
        Post(
            id = it.id,
            title = it.title,
            text = it.text,
            imageUrl = it.imageUrl,
            sort = it.sort,
            date = it.date,
        )
    }

internal val List<Post>.asListPostLocalModel: List<PostLocalModel>
    get() = this.map {
        PostLocalModel(
            id = it.id,
            title = it.title,
            text = it.text,
            imageUrl = it.imageUrl,
            sort = it.sort,
            date = it.date,
        )
    }