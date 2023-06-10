package uz.hayot.vitaInLine.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import uz.hayot.vitaInLine.R


open class BaseAlertDialog(context: Context) : AlertDialog(context) {
    init {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.attributes.windowAnimations = R.style.DialogAnimationZoom
        window!!.attributes.gravity = Gravity.CENTER
    }
}