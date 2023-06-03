package uz.hayot.vitaInLine.util.functions

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import java.text.SimpleDateFormat
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


        fun convertShortDesc(description: String): String {
            return description.substring(0, 30) + "..."
        }
    }


}