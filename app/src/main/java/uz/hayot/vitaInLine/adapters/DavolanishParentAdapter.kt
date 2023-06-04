package uz.hayot.vitaInLine.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.data.model.DataItem
import uz.hayot.vitaInLine.data.model.HealingChildRVModel
import uz.hayot.vitaInLine.databinding.DavolanishParentRowBinding

class DavolanishParentAdapter(private val healingList: List<DataItem>) :
    RecyclerView.Adapter<DavolanishParentAdapter.ViewHolder>() {
    private var infoListener: OnParentInfoClickedListener? = null
    private var childList: MutableList<HealingChildRVModel> = ArrayList()

    inner class ViewHolder(private val binding: DavolanishParentRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(dataItem: DataItem) {
            binding.davParentPillName.text = dataItem.pill
            childList.clear()
            for (i in 0 until (dataItem.times?.size ?: 0)) {
                childList.add(
                    HealingChildRVModel(
                        dataItem.times?.get(i) ?: "",
                        dataItem.quantity.toString(),
                        dataItem.type.toString()
                    )
                )
            }

            binding.davChildRv.adapter =
                DavolanishChildAdapter(childList)

            binding.davParentInfoIcon.setOnClickListener {
                infoListener?.onInfoClicked(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            DavolanishParentRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(healingList[position])


    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    override fun getItemCount(): Int {
        return healingList.size
    }

    fun setOnInfoClicked(listener: OnParentInfoClickedListener) {
        this.infoListener = listener
    }

    interface OnParentInfoClickedListener {
        fun onInfoClicked(position: Int) {

        }
    }
}