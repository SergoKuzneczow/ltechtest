package com.sergokuzneczow.domain.phone_mask_converter_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CalculatePhoneMaskPrefixCaseTest {

    private val calculatePhoneMaskPrefixCaseApi: CalculatePhoneMaskPrefixCaseApi = CalculatePhoneMaskPrefixCaseImpl()

    @Test
    fun `entering the correct phone mask`() {
        val masks: List<String> = listOf("+7 (ХХХ) ХХХ-ХХ-ХХ", "+44 ХХХХ-ХХХХХХ")
        val expected: List<String> = listOf("+7", "+44")
        assertThat(calculatePhoneMaskPrefixCaseApi.execute(masks[0])).isEqualTo(expected[0])
    }

    @Test
    fun `entering the empty phone mask`() {
        val masks: List<String> = listOf("", "")
        val expected: List<String> = listOf("", "")
        assertThat(calculatePhoneMaskPrefixCaseApi.execute(masks[0])).isEqualTo(expected[0])
    }
}