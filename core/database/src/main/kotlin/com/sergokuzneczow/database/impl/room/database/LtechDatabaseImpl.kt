package com.sergokuzneczow.database.impl.room.database

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
internal class LtechDatabaseImpl @Inject constructor(@ApplicationContext context: Context) {

    val database: LtechDatabaseApi = Room.databaseBuilder(context, LtechDatabaseApi::class.java, "ltech_database").build()
}