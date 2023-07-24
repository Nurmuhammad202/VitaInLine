package uz.hayot.vitaInLine.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import uz.hayot.vitaInLine.databinding.ItemPrrofileDataDialogLayoutBinding

class DostupDialog(context: Context,var success: (String) -> Unit):BaseAlertDialog(context) {
    private val binding = ItemPrrofileDataDialogLayoutBinding.inflate(LayoutInflater.from(context))

}