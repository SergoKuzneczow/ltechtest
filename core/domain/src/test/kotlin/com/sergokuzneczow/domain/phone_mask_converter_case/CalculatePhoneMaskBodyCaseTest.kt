package com.sergokuzneczow.domain.phone_mask_converter_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CalculatePhoneMaskBodyCaseTest {

    private val calculatePhoneMaskBodyCaseApi: CalculatePhoneMaskBodyCaseApi = CalculatePhoneMaskBodyCaseImpl()

    @Test
    fun `entering the correct phone mask`() {
        val masks: List<String> = listOf("+7 (ХХХ) ХХХ-ХХ-ХХ", "+44 ХХХХ-ХХХХХХ")
        val expected: List<String> = listOf("(ХХХ) ХХХ-ХХ-ХХ", "ХХХХ-ХХХХХХ")
        assertThat(calculatePhoneMaskBodyCaseApi.execute(masks[0])).isEqualTo(expected[0])
    }

    @Test
    fun `entering the empty phone mask`() {
        val masks: List<String> = listOf("", "")
        val expected: List<String> = listOf("", "")
        assertThat(calculatePhoneMaskBodyCaseApi.execute(masks[0])).isEqualTo(expected[0])
    }
}