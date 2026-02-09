package com.hyunbindev.minimo.utill

import java.time.LocalDate
import java.time.LocalDateTime

object DateUtil {
    fun fromLocalDateTimeToFormatString(localDate: LocalDateTime): String {
        val year = localDate.year
        val month = localDate.monthValue
        val date = localDate.dayOfMonth
        val hour = localDate.hour
        val minute = localDate.minute

        return "$year-$month-$date  $hour:$minute"
    }
}