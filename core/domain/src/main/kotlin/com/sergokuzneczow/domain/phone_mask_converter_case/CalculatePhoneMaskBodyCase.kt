package com.sergokuzneczow.domain.phone_mask_converter_case

import jakarta.inject.Inject

public interface CalculatePhoneMaskBodyCaseApi {

    public fun execute(mask: String): String
}

public class CalculatePhoneMaskBodyCaseImpl @Inject constructor() : CalculatePhoneMaskBodyCaseApi {

    override fun execute(mask: String): String {
        var maskBody: String = ""
        var isBody: Boolean = false
        for (char in mask) {
            if (isBody) maskBody += char
            if (char == ' ') isBody = true
        }
        return maskBody
    }
}