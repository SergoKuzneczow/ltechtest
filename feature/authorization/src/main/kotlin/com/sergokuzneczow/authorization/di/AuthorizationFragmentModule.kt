package com.sergokuzneczow.authorization.di

import com.sergokuzneczow.domain.phone_mask_converter_case.CalculatePhoneMaskBodyCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.CalculatePhoneMaskBodyCaseImpl
import com.sergokuzneczow.domain.phone_mask_converter_case.CalculatePhoneMaskPrefixCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.CalculatePhoneMaskPrefixCaseImpl
import com.sergokuzneczow.domain.phone_mask_converter_case.DeletePhoneMaskCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.DeletePhoneMaskCaseImpl
import com.sergokuzneczow.domain.phone_mask_converter_case.PhoneMaskBodyConverterCaseApi
import com.sergokuzneczow.domain.phone_mask_converter_case.PhoneMaskBodyConverterCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal interface AuthorizationFragmentModule {

    @Binds
    @ViewModelScoped
    fun toCalculatePhoneMaskPrefixCaseApiCalculatePhoneMaskBodyCaseApi(calculatePhoneMaskPrefixCaseImpl: CalculatePhoneMaskPrefixCaseImpl): CalculatePhoneMaskPrefixCaseApi

    @Binds
    @ViewModelScoped
    fun toCalculatePhoneMaskBodyCaseApi(calculatePhoneMaskBodyCaseImpl: CalculatePhoneMaskBodyCaseImpl): CalculatePhoneMaskBodyCaseApi

    @Binds
    @ViewModelScoped
    fun toPhoneMaskBodyConverterCaseApi(phoneMaskBodConverterCaseImpl: PhoneMaskBodyConverterCaseImpl): PhoneMaskBodyConverterCaseApi

    @Binds
    @ViewModelScoped
    fun toDeletePhoneMaskCaseApi(deletePhoneMaskCaseImpl: DeletePhoneMaskCaseImpl): DeletePhoneMaskCaseApi
}