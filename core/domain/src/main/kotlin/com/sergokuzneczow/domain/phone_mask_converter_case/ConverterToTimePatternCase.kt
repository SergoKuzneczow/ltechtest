package com.sergokuzneczow.domain.phone_mask_converter_case

import android.os.Build
import jakarta.inject.Inject
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.time.Instant
import kotlin.time.toJavaInstant

public interface ConverterToTimePatterCaseApi {

    public fun execute(timestamp: Long): String
}

public class ConverterToTimePatternCaseImpl @Inject constructor() : ConverterToTimePatterCaseApi {

    public override fun execute(timestamp: Long): String {
        val instant = if (this.toString().length == 10) Instant.fromEpochSeconds(timestamp)
        else Instant.fromEpochMilliseconds(timestamp)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd MMMM, HH:mm")
                .withZone(ZoneId.systemDefault())
                .withLocale(Locale("ru"))
                .format(instant.toJavaInstant())
        } else {
            val sdf = SimpleDateFormat("dd MMMM, HH:mm", Locale("ru"))
            sdf.timeZone = TimeZone.getDefault()
            sdf.format(Date(timestamp))
        }
    }
}