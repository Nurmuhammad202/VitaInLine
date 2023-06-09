package uz.hayot.vitaInLine.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.data.model.HealingChildRVModel
import uz.hayot.vitaInLine.databinding.DavolanishChildRowBinding
import uz.hayot.vitaInLine.util.Constants

class DavolanishChildAdapter(private val childList: MutableList<HealingChildRVModel>,private val context: Context) :
    RecyclerView.Adapter<DavolanishChildAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: DavolanishChildRowBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun onBind(healingChildRVModel: HealingChildRVModel) {
            if (healingChildRVModel.sectionType == "recommendations") {
                binding.davChildPillCount.text = ""
                binding.davChildPillDesc.text = ""
                binding.davChildDescContainer.visibility = View.INVISIBLE
                binding.davChildPillTime.text = healingChildRVModel.time
            } else {
                binding.davChildDescContainer.visibility = View.VISIBLE
                binding.davChildPillTime.text = healingChildRVModel.time
                binding.davChildPillCount.text = healingChildRVModel.quantity +" "+ context.resources.getString(
                    R.string.tabletka_tx)
                if (healingChildRVModel.type == Constants.BEFORE_MEAL)
                    binding.davChildPillDesc.text = context.resources.getString(R.string.before_meal)
                else if (healingChildRVModel.type == Constants.AFTER_MEAL)
                    binding.davChildPillDesc.text = context.resources.getString(R.string.after_meal)

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            DavolanishChildRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(childList[position])
    }
}