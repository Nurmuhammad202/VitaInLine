package uz.hayot.vitaInLine.ui.dialog

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import uz.hayot.vitaInLine.databinding.ItemPrrofileDataDialogLayoutBinding
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class DataPicterDialog(context: Context, var success: (String) -> Unit) :
    BaseAlertDialog(context) {
    private val binding = ItemPrrofileDataDialogLayoutBinding.inflate(LayoutInflater.from(context))

    init {

        //min date...
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        binding.datePicker.maxDate = calendar.timeInMillis

        binding.apply {
            btClose.setOnClickListener {
                dismiss()
            }

            btnSave.setOnClickListener {
                val day: String = if (binding.datePicker.dayOfMonth <10) {
                    "0${binding.datePicker.dayOfMonth}"
                } else {
                    binding.datePicker.dayOfMonth.toString()
                }
                val month = if(binding.datePicker.month<9){
                    "0${binding.datePicker.month+1}"
                }else {
                    (binding.datePicker.month+1).toString()
                }
                Log.e("month", "$month: ")
                success("${day}.${month}.${binding.datePicker.year}")
                dismiss()
            }
        }

        setView(binding.root)
    }


}