package uz.hayot.vitaInLine.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import uz.hayot.vitaInLine.databinding.DavolanishParentRowBinding

import uz.hayot.vitaInLine.models.ParentRcModel

class DavolanishParentAdapter(private val parentList: List<ParentRcModel>) :
    RecyclerView.Adapter<DavolanishParentAdapter.ViewHolder>() {
    private var infoListener: OnParentInfoClickedListener? = null

    inner class ViewHolder(private val binding: DavolanishParentRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(parentRcModel: ParentRcModel) {
            binding.davParentPillName.text = parentRcModel.title
            binding.davChildRv.adapter = DavolanishChildAdapter(parentRcModel.list)

            binding.davParentIcon.setOnClickListener {
                infoListener?.onInfoClicked(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DavolanishParentRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(parentList[position])


    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    override fun getItemCount(): Int {
        return parentList.size
    }

    fun setOnInfoClicked(listener: OnParentInfoClickedListener) {
        this.infoListener = listener
    }

    interface OnParentInfoClickedListener {
        fun onInfoClicked(position: Int) {

        }
    }
}