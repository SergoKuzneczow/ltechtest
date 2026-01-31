package com.sergokuzneczow.domain.phone_mask_converter_case

import jakarta.inject.Inject

public interface CalculatePhoneMaskPrefixCaseApi {

    public fun execute(mask: String): String
}

public class CalculatePhoneMaskPrefixCaseImpl @Inject constructor() : CalculatePhoneMaskPrefixCaseApi {

    override fun execute(mask: String): String {
        var maskPrefix: String = ""
        for (char in mask) {
            if (char == ' ') return maskPrefix
            else maskPrefix += char
        }
        return maskPrefix
    }
}