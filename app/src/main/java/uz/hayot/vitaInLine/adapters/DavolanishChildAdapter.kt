package uz.hayot.vitaInLine.adapters


import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.databinding.DavolanishChildRowBinding
import uz.hayot.vitaInLine.models.ChildRcModel

class DavolanishChildAdapter(private val childList: MutableList<ChildRcModel>) :
    RecyclerView.Adapter<DavolanishChildAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: DavolanishChildRowBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun onBind(childRcModel: ChildRcModel) {
            binding.davChildPillTime.text = childRcModel.time
            binding.davChildPillCount.text = childRcModel.pillCount
            binding.davChildPillDesc.text = childRcModel.pillDesc



            binding.root.setOnClickListener {
                if (binding.davChildCheckBox.isSelected) {
                    binding.davChildCheckBox.isSelected = false
                    binding.root.isSelected = false
                    binding.davChildPillCount.isSelected = false
                    binding.davChildPillTime.isSelected = false
                    binding.davChildDescContainer.visibility = View.VISIBLE
                    binding.davChildPillCount.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.third_text_color
                        )
                    )


                } else {
                    binding.davChildCheckBox.isSelected = true
                    binding.root.isSelected = true
                    binding.davChildPillCount.isSelected = true
                    binding.davChildPillTime.isSelected = true
                    binding.davChildDescContainer.visibility = View.GONE
                    binding.davChildPillCount.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.child_count_text_selected_color
                        )
                    )


                }

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