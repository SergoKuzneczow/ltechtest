package com.sergokuzneczow.database.api

import com.sergokuzneczow.model.AuthenticateRequest
import com.sergokuzneczow.model.Post
import com.sergokuzneczow.model.Sorting
import kotlinx.coroutines.flow.Flow

public interface DatabaseDataSourceApi {

    public suspend fun setAuthenticateRequest(authenticateRequest: AuthenticateRequest)

    public suspend fun getAuthenticateRequest(phoneMask: String): AuthenticateRequest?

    public fun getPosts(): Flow<List<Post>>

    public fun getPostsBySorting(sorting: Sorting): Flow<List<Post>>

    public suspend fun setPosts(posts: List<Post>)
}