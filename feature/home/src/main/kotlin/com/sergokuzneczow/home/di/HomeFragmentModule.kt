package com.sergokuzneczow.home.di

import com.sergokuzneczow.domain.phone_mask_converter_case.ConverterToTimePatterCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.ConverterToTimePatternCaseImpl
import com.sergokuzneczow.domain.phone_mask_converter_case.SyncNowPostSourcesCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.SyncNowPostSourcesCaseImpl
import com.sergokuzneczow.domain.phone_mask_converter_case.SyncPostSourcesCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.SyncPostSourcesCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal interface HomeFragmentModule {

    @Binds
    @FragmentScoped
    fun toSyncPostSourcesCaseApi(syncPostSourcesCaseImpl: SyncPostSourcesCaseImpl): SyncPostSourcesCaseApi

    @Binds
    @FragmentScoped
    fun toSyncNowPostSourcesCaseApi(syncNowPostSourcesCaseImpl: SyncNowPostSourcesCaseImpl): SyncNowPostSourcesCaseApi

    @Binds
    @FragmentScoped
    fun toConverterToTimePatterCaseApi(converterToTimePatternCaseImpl: ConverterToTimePatternCaseImpl): ConverterToTimePatterCaseApi
}