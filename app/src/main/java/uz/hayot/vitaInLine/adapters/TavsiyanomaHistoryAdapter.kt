package uz.hayot.vitaInLine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.databinding.DavolanishParentRowBinding
import uz.hayot.vitaInLine.databinding.HistoryDateRowBinding
import uz.hayot.vitaInLine.databinding.TavsiyanomaParentRowBinding
import uz.hayot.vitaInLine.models.DateModel
import uz.hayot.vitaInLine.models.ParentRcModel

class TavsiyanomaHistoryAdapter(private val list: MutableList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val PARENT_VIEW_TYPE = 0
        const val DATE_VIEW_TYPE = 1
    }

    inner class ParentViewHolder(private val binding: TavsiyanomaParentRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(parentRcModel: ParentRcModel) {
            binding.tavParentRvName.text = parentRcModel.title
            binding.tavChildRv.adapter = TavsiyanomaChildAdapter(parentRcModel.list)
        }
    }

    inner class DateViewHolder(private val binding: HistoryDateRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(dateModel: DateModel) {
            binding.historyDate.text = dateModel.date

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PARENT_VIEW_TYPE -> {
                val view = TavsiyanomaParentRowBinding.inflate(
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
                personViewHolder.onBind(person)
            }
            DATE_VIEW_TYPE -> {
                val dateViewHolder = holder as DateViewHolder
                val date = item as DateModel
                dateViewHolder.onBind(date)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(list[position] is DateModel)
            DATE_VIEW_TYPE
        else PARENT_VIEW_TYPE
    }
}