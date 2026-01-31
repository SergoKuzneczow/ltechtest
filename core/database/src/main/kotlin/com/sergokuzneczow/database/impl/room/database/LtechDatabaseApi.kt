package com.sergokuzneczow.database.impl.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergokuzneczow.database.impl.room.dao.AuthenticateRequestDao
import com.sergokuzneczow.database.impl.room.entity.AuthenticateRequestLocalModel

@Database(
    version = 1,
    entities = [AuthenticateRequestLocalModel::class],
    exportSchema = false,
)
internal abstract class LtechDatabaseApi : RoomDatabase() {

    abstract fun getAuthenticateRequestDao(): AuthenticateRequestDao
}