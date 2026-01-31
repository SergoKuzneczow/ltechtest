package com.sergokuzneczow.domain.phone_mask_converter_case

import jakarta.inject.Inject

public interface DeletePhoneMaskCaseApi {
    public fun execute(prefix: String, body: String): String
}

public class DeletePhoneMaskCaseImpl @Inject constructor() : DeletePhoneMaskCaseApi {

    override fun execute(prefix: String, body: String): String {
        return (prefix + body).filter { it.isDigit() }
    }
}