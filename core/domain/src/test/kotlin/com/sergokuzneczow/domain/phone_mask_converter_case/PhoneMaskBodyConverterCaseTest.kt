package com.sergokuzneczow.domain.phone_mask_converter_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PhoneMaskBodyConverterCaseTest {

    private val phoneMaskBodyConverterCaseApi: PhoneMaskBodyConverterCaseApi = PhoneMaskBodyConverterCaseImpl()

    private val masks: List<String> = listOf("(ХХХ) ХХХ-ХХ-ХХ", "ХХХХ-ХХХХХХ")

    @Test
    fun `when enter empty current string`() {
        val expected: List<String> = listOf("(", "")
        val input: List<String> = listOf("", "")

        assertThat(phoneMaskBodyConverterCaseApi.execute(masks[0], input[0])).isEqualTo(expected[0])
        assertThat(phoneMaskBodyConverterCaseApi.execute(masks[1], input[1])).isEqualTo(expected[1])
    }

    @Test
    fun `when part of the mask was removed`() {
        val expected: List<String> = listOf("(00", "0")
        val input: List<String> = listOf("(00", "0")

        assertThat(phoneMaskBodyConverterCaseApi.execute(masks[0], input[0])).isEqualTo(expected[0])
        assertThat(phoneMaskBodyConverterCaseApi.execute(masks[1], input[1])).isEqualTo(expected[1])
    }

    @Test
    fun `the entered characters must be separated`() {
        val expected: List<String> = listOf("(000) 0", "0000-0")
        val input: List<String> = listOf("(0000", "00000")

        assertThat(phoneMaskBodyConverterCaseApi.execute(masks[0], input[0])).isEqualTo(expected[0])
        assertThat(phoneMaskBodyConverterCaseApi.execute(masks[1], input[1])).isEqualTo(expected[1])
    }
}