package uz.hayot.vitaInLine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.databinding.TavsiyanomaChildRowBinding


import uz.hayot.vitaInLine.models.ChildRcModel

class TavsiyanomaChildAdapter(private val childList: MutableList<ChildRcModel>) :
    RecyclerView.Adapter<TavsiyanomaChildAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: TavsiyanomaChildRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(childRcModel: ChildRcModel) {
            binding.tavChildPillTime.text = childRcModel.time

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            TavsiyanomaChildRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(childList[position])
    }
}