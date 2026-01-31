package com.sergokuzneczow.domain.phone_mask_converter_case

import jakarta.inject.Inject
import kotlin.text.iterator

public interface PhoneMaskBodyConverterCaseApi {
    public fun execute(maskBody: String, current: String): String
}

public class PhoneMaskBodyConverterCaseImpl @Inject constructor() : PhoneMaskBodyConverterCaseApi {

    override fun execute(maskBody: String, current: String): String {
        val currentOnlyDigit: String = current.filter { it.isDigit() }
        var indexCurrentOnlyDigit: Int = 0
        var res: String = ""

        for (maskChar: Char in maskBody) {
            when (maskChar) {
                '+' -> res += '+'
                '-' -> res += '-'
                '(' -> res += '('
                ')' -> res += ')'
                ' ' -> res += ' '
                'X', 'x', 'Х', 'х' -> {
                    val nextNumber: Char? = currentOnlyDigit.getOrNull(indexCurrentOnlyDigit)
                    if (nextNumber != null) {
                        res += nextNumber
                        indexCurrentOnlyDigit++
                    } else return res
                }
            }
            if (currentOnlyDigit.getOrNull(indexCurrentOnlyDigit) == null) return res
        }
        return res
    }
}