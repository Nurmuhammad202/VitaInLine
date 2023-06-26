package uz.hayot.vitaInLine.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.data.model.NotificationChild
import uz.hayot.vitaInLine.databinding.NotifRvRowBinding

class NotificationChildAdapter(private val list: MutableList<NotificationChild>) :
    RecyclerView.Adapter<NotificationChildAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: NotifRvRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(notificationChild: NotificationChild) {
            binding.notifRvTitle.text = notificationChild.pillName
            binding.notifRvExtraPillCount.text = notificationChild.pillCount+ " ta tabletka"
            binding.notifRvExtraDesc.text = notificationChild.pillStatus
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = NotifRvRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }
}