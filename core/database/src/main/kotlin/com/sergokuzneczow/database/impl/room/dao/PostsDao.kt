package com.sergokuzneczow.database.impl.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sergokuzneczow.database.impl.room.entity.PostLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrReplacePosts(columns: List<PostLocalModel>)

    @Query("select * from posts")
    fun queryPosts(): Flow<List<PostLocalModel>>

    @Query("select * from posts order by date asc")
    fun queryPostsByDate(): Flow<List<PostLocalModel>>

    @Query("select * from posts order by sort asc")
    fun queryPostsByServerSort(): Flow<List<PostLocalModel>>
}