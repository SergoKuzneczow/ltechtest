package com.sergokuzneczow.database.impl.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergokuzneczow.database.impl.room.dao.AuthenticateRequestDao
import com.sergokuzneczow.database.impl.room.dao.PostsDao
import com.sergokuzneczow.database.impl.room.entity.AuthenticateRequestLocalModel
import com.sergokuzneczow.database.impl.room.entity.PostLocalModel

@Database(
    version = 4,
    entities = [AuthenticateRequestLocalModel::class, PostLocalModel::class],
    exportSchema = false,
)
internal abstract class LtechDatabaseApi : RoomDatabase() {

    abstract fun getAuthenticateRequestDao(): AuthenticateRequestDao
    abstract fun getPostsDao(): PostsDao
}