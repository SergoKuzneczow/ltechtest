package com.sergokuzneczow.model

public data class Post(
    val id: String,
    val title: String,
    val text: String,
    val imageUrl: String,
    val sort: Int,
    val date: Long,
)