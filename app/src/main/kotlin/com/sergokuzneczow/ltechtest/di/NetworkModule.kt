package com.sergokuzneczow.ltechtest.di

import com.sergokuzneczow.network.api.NetworkDataSourceApi
import com.sergokuzneczow.network.impl.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkModule {

    @Binds
    @Singleton
    fun to(networkDataSourceImpl: NetworkDataSourceImpl): NetworkDataSourceApi
}