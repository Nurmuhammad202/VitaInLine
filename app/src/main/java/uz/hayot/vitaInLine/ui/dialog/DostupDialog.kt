package uz.hayot.vitaInLine.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import uz.hayot.vitaInLine.databinding.ItemDostupDialogBinding
import uz.hayot.vitaInLine.databinding.ItemPrrofileDataDialogLayoutBinding

class DostupDialog(context: Context,var success: (Boolean) -> Unit):BaseAlertDialog(context) {
    private val binding = ItemDostupDialogBinding.inflate(LayoutInflater.from(context))

    init {
        binding.dostupNoBtn.setOnClickListener{
            success(false)
            dismiss()
        }
        binding.dostupYesBtn.setOnClickListener{
            success(true)
            dismiss()
        }

        setView(binding.root)
    }

}