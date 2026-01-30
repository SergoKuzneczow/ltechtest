package com.sergokuzneczow.ltechtest.di

import com.sergokuzneczow.ltechtest.navigator.NavigatorImpl
import com.sergokuzneczow.navigator.NavigatorApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NavigatorModule {

    @Binds
    @Singleton
    fun to(navigatorImpl: NavigatorImpl): NavigatorApi
}