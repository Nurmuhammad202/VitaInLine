package uz.hayot.vitaInLine.util.functions

import android.annotation.SuppressLint
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import uz.hayot.vitaInLine.data.local.room.entity.PillModel
import uz.hayot.vitaInLine.data.local.room.entity.RecommendationModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors
import kotlin.math.abs

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


        fun convertShortDesc(description: String, startPosition: Int, endPosition: Int): String {
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

        @RequiresApi(Build.VERSION_CODES.N)
        fun getDifferentPillId(
            oldList: List<PillModel>,
            newList: List<PillModel>
        ): MutableList<PillModel> {
            val result: MutableList<PillModel> = ArrayList()
            for (l2 in newList) {
                if (oldList.stream().filter { l1: PillModel -> l1.id != l2.id }
                        .collect(Collectors.toList()).size == oldList.size) {
                    result.add(l2)
                }
            }
            return result
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun getDifferentRecommadationId(
            oldList: List<RecommendationModel>,
            newList: List<RecommendationModel>
        ): MutableList<RecommendationModel> {
            val result: MutableList<RecommendationModel> = ArrayList()
            for (l2 in newList) {
                if (oldList.stream().filter { l1: RecommendationModel -> l1.id != l2.id }
                        .collect(Collectors.toList()).size == oldList.size) {
                    result.add(l2)
                }
            }
            return result
        }

        @SuppressLint("SimpleDateFormat")
        fun determineDefferenceDate(inputDate: String): Int {
            var daysDiff: Long = -1
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            try {
                // Hozirgi vaqni olish
                val currentDate = Date()

                // format bo'yicha parse qilish
                val givenDate: Date = dateFormat.parse(inputDate) as Date

                // vaqt farqini hisoblash
                val timeDiff = abs(currentDate.time - givenDate.time)

                // farqni kun hisobida hisoblash
                daysDiff = timeDiff / (24 * 60 * 60 * 1000) + 1

            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return daysDiff.toInt()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun convertTimeUseDate(time: String, endDate: String): List<Long> {
            val result: MutableList<Long> = ArrayList()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val retDay = determineDefferenceDate(endDate)

            for (i in 0 until retDay) {
                val localTime = LocalTime.parse(time, formatter)
                val localDateTime =
                    LocalDateTime.of(LocalDate.now().plusDays(i.toLong()), localTime)

                result.add(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
            }

            return result
        }

    }

}