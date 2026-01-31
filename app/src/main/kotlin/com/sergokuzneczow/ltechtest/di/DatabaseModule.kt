package com.sergokuzneczow.ltechtest.di

import com.sergokuzneczow.database.api.DatabaseDataSourceApi
import com.sergokuzneczow.database.impl.DatabaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DatabaseModule {

    @Binds
    @Singleton
    fun toDatabaseDataSourceApi(databaseDataSourceImpl: DatabaseDataSourceImpl): DatabaseDataSourceApi
}