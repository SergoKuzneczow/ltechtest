package com.sergokuzneczow.database.impl

import com.sergokuzneczow.database.api.DatabaseDataSourceApi
import com.sergokuzneczow.database.impl.room.dao.AuthenticateRequestDao
import com.sergokuzneczow.database.impl.room.dao.PostsDao
import com.sergokuzneczow.database.impl.room.database.LtechDatabaseImpl
import com.sergokuzneczow.database.impl.room.entity.asAuthenticateRequest
import com.sergokuzneczow.database.impl.room.entity.asAuthenticateRequestLocalModel
import com.sergokuzneczow.database.impl.room.entity.asListPostLocalModel
import com.sergokuzneczow.database.impl.room.entity.asListPosts
import com.sergokuzneczow.model.AuthenticateRequest
import com.sergokuzneczow.model.Post
import com.sergokuzneczow.model.Sorting
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

public class DatabaseDataSourceImpl private constructor(
    private val authenticateRequestDao: AuthenticateRequestDao,
    private val postsDao: PostsDao,
) : DatabaseDataSourceApi {

    @Inject
    internal constructor(database: LtechDatabaseImpl) : this(database.database.getAuthenticateRequestDao(), database.database.getPostsDao())

    override suspend fun setAuthenticateRequest(authenticateRequest: AuthenticateRequest) {
        authenticateRequestDao.insertOrReplace(authenticateRequest.asAuthenticateRequestLocalModel)
    }

    override suspend fun getAuthenticateRequest(phoneMask: String): AuthenticateRequest? = authenticateRequestDao.queryLastAuthenticateRequestColumn(phoneMask)?.asAuthenticateRequest

    override fun getPosts(): Flow<List<Post>> = postsDao.queryPosts().map { it.asListPosts }

    override fun getPostsBySorting(sorting: Sorting): Flow<List<Post>> {
        return when (sorting) {
            Sorting.DATE -> postsDao.queryPostsByDate().map { it.asListPosts }
            Sorting.DEFAULT -> postsDao.queryPostsByServerSort().map { it.asListPosts }
        }
    }

    override fun getPostsByKey(key: String): Flow<List<Post>> = postsDao.queryPostsByKey(key).map { it.asListPosts }

    override suspend fun setPosts(posts: List<Post>) {
        postsDao.insertOrReplacePosts(posts.asListPostLocalModel)
    }
}