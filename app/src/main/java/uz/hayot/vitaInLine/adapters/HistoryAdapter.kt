package uz.hayot.vitaInLine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.databinding.DavolanishParentRowBinding
import uz.hayot.vitaInLine.databinding.HistoryDateRowBinding
import uz.hayot.vitaInLine.models.DateModel
import uz.hayot.vitaInLine.models.ParentRcModel

class HistoryAdapter(private val list: MutableList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val PARENT_VIEW_TYPE = 0
        const val DATE_VIEW_TYPE = 1
    }

    inner class ParentViewHolder(private val binding: DavolanishParentRowBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(parentRcModel: ParentRcModel) {
            binding.davParentPillName.text = parentRcModel.title
            binding.davChildRv.adapter = DavolanishChildAdapter(parentRcModel.list)
        }
    }

    inner class DateViewHolder(private val binding: HistoryDateRowBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(dateModel: DateModel) {
            binding.historyDate.text = dateModel.date

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PARENT_VIEW_TYPE -> {
                val view = DavolanishParentRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ParentViewHolder(view)
            }
            DATE_VIEW_TYPE -> {
                val view = HistoryDateRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DateViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (holder.itemViewType) {
            PARENT_VIEW_TYPE -> {
                val personViewHolder = holder as ParentViewHolder
                val person = item as ParentRcModel
                personViewHolder.bind(person)
            }
            DATE_VIEW_TYPE -> {
                val dogViewHolder = holder as DateViewHolder
                val dog = item as DateModel
                dogViewHolder.bind(dog)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is ParentRcModel -> PARENT_VIEW_TYPE
            is DateModel -> DATE_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid data type")
        }
    }
}