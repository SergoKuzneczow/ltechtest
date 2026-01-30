package com.sergokuzneczow.network.impl.models

import com.sergokuzneczow.model.PhoneMask

internal data class PhoneMaskRemoteModel(
    val phoneMask: String
)

internal val PhoneMaskRemoteModel.asPhoneMask: PhoneMask
    get() = PhoneMask(
        phoneMask = this.phoneMask
    )

internal val PhoneMask.asPhoneMaskRemoteModel: PhoneMaskRemoteModel
    get() = PhoneMaskRemoteModel(
        phoneMask = this.phoneMask
    )