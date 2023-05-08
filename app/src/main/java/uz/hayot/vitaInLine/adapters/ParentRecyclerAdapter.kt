package uz.hayot.vitaInLine.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.databinding.DavolanishItemBinding

import uz.hayot.vitaInLine.models.ParentRcModel

class ParentRecyclerAdapter(private val parentList: List<ParentRcModel>) :
    RecyclerView.Adapter<ParentRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: DavolanishItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(parentRcModel: ParentRcModel) {
            binding.parentRcPillName.text = parentRcModel.title
            binding.childRecyclerView.adapter = ChildRecyclerAdapter(parentRcModel.list)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DavolanishItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(parentList[position])


    }

    override fun getItemCount(): Int {
        return parentList.size
    }
}