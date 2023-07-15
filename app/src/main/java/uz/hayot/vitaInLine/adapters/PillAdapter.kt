package uz.hayot.vitaInLine.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.data.model.DataPill
import uz.hayot.vitaInLine.databinding.PillsRowBinding
import uz.hayot.vitaInLine.util.functions.ExtraFunctions.Companion.convertShortDesc

class PillAdapter(private var list: List<DataPill>) :
    RecyclerView.Adapter<PillAdapter.ViewHolder>() {

    private var pillsListener: OnPillsClickedListener? = null

    inner class ViewHolder(private val binding: PillsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(pill: DataPill) {

            binding.pillsName.text = pill.title
            if (pill.description!!.length > 29) {
                binding.pillsShortDesc.text = convertShortDesc(pill.description, 0, 30)
            } else {
                binding.pillsShortDesc.text = pill.description
            }

            binding.root.setOnClickListener {
                pillsListener?.onPillsClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PillsRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<DataPill>) {
        list = newData
        notifyDataSetChanged()
    }

    fun setOnPillsClicked(listener: OnPillsClickedListener) {
        this.pillsListener = listener

    }

    interface OnPillsClickedListener {
        fun onPillsClicked(position: Int)
    }
}