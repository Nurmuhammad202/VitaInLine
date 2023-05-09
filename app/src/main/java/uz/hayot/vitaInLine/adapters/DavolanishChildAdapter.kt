package uz.hayot.vitaInLine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(childList[position])
    }
}