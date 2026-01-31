package com.sergokuzneczow.domain.phone_mask_converter_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test

internal class DeletePhoneMaskCaseTest {

    private val deletePhoneMaskCaseApi: DeletePhoneMaskCaseApi = DeletePhoneMaskCaseImpl()

    @Test
    fun `happy path`() {
        val expected: List<String> = listOf("71112233", "441122333444")

        val prefix1 = "+7"
        val body1 = " 111-22-33"

        val prefix2 = "+44"
        val body2 = " 1122-333444"

        assertThat(deletePhoneMaskCaseApi.execute(prefix1, body1)).isEqualTo(expected[0])
        assertThat(deletePhoneMaskCaseApi.execute(prefix2, body2)).isEqualTo(expected[1])
    }
}