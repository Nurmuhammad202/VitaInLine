package uz.hayot.vitaInLine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.data.model.advertising.Data
import uz.hayot.vitaInLine.databinding.PillsRowBinding
import uz.hayot.vitaInLine.models.Pill
import uz.hayot.vitaInLine.util.functions.ExtraFunctions
import uz.hayot.vitaInLine.util.functions.ExtraFunctions.Companion.convertShortDesc

class PillAdapter(private val list: ArrayList<Data>) :
    RecyclerView.Adapter<PillAdapter.ViewHolder>() {
    private var pillsListener: OnPillsClickedListener? = null

    inner class ViewHolder(private val binding: PillsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(pill: Data) {
            //binding.pillsImage.setImageResource(pill.pillIcon)
            binding.pillsName.text = pill.title
            binding.pillsShortDesc.text = convertShortDesc(pill.description,0,30)
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