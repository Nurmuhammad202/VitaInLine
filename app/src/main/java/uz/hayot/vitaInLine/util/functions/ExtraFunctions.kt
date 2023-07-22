package uz.hayot.vitaInLine.util.functions

import android.annotation.SuppressLint
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class ExtraFunctions {
    companion object {
        fun twoColoredText(
            item: String,
            startPosition: Int,
            endPosition: Int,
            startColor: Int,
            endColor: Int

        ): Spannable {

            val spannable = SpannableString(item)
            spannable.setSpan(
                ForegroundColorSpan(startColor),
                startPosition, endPosition,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                ForegroundColorSpan(endColor),
                endPosition + 1, item.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannable
        }


        fun convertShortDesc(description: String,startPosition: Int,endPosition: Int): String {
            return description.substring(startPosition, endPosition) + "..."
        }

        fun convertTimeStringToMillis(time: String): Long {
            val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern("HH:mm")
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val localTime = LocalTime.parse(time, formatter)
            val localDateTime = LocalDateTime.of(LocalDate.now(), localTime)
            return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }


    }


}