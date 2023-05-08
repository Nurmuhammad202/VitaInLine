package uz.hayot.vitaInLine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.databinding.PillsRowBinding
import uz.hayot.vitaInLine.models.Pill
import uz.hayot.vitaInLine.util.functions.ExtraFunctions

class PillAdapter(private val list: MutableList<Pill>) :
    RecyclerView.Adapter<PillAdapter.ViewHolder>() {
    private var pillsListener: OnPillsClickedListener? = null

    inner class ViewHolder(private val binding: PillsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(pill: Pill) {
            binding.pillsImage.setImageResource(pill.pillIcon)
            binding.pillsName.text = pill.pillName
            binding.pillsShortDesc.text = ExtraFunctions.convertShortDesc(pill.pillDescription)
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

    fun setOnPillsClicked(listener: OnPillsClickedListener) {
        this.pillsListener = listener

    }

    interface OnPillsClickedListener {
        fun onPillsClicked(position: Int)
    }
}