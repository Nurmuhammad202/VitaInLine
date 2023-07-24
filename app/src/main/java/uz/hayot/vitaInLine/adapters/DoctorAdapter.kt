package uz.hayot.vitaInLine.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.data.model.DataItem
import uz.hayot.vitaInLine.data.model.HealingChildRVModel
import uz.hayot.vitaInLine.data.model.doctorById.CustomDoctorById
import uz.hayot.vitaInLine.databinding.DavolanishParentRowBinding
import uz.hayot.vitaInLine.databinding.ItemRecyclerviewDoctorsBinding

class DoctorAdapter(
    private val list: ArrayList<CustomDoctorById>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRecyclerviewDoctorsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(dataItem: CustomDoctorById) {
            binding.apply {
                txtName.text = dataItem.userName
                txtSpecialty.text = dataItem.specialty
                layout.setOnClickListener {
                    onItemClickListener.onItemClickListener(dataItem.confirmationId)
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClickListener(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemRecyclerviewDoctorsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}