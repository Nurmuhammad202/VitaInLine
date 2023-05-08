package uz.hayot.vitaInLine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.databinding.ChildRecyclerItemBinding
import uz.hayot.vitaInLine.models.ChildRcModel

class ChildRecyclerAdapter(private val childList: MutableList<ChildRcModel>) :
    RecyclerView.Adapter<ChildRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ChildRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(childRcModel: ChildRcModel) {
            binding.rcChildPillTime.text = childRcModel.time
            binding.rcChildPillCount.text = childRcModel.pillCount
            binding.rcChildPillDesc.text = childRcModel.pillDesc
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ChildRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(childList[position])
    }
}