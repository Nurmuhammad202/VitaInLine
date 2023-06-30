package uz.hayot.vitaInLine.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.data.model.NotificationChild
import uz.hayot.vitaInLine.databinding.NotifRvRowBinding
import uz.hayot.vitaInLine.util.Constants.AFTER_MEAL
import uz.hayot.vitaInLine.util.Constants.BEFORE_MEAL

class NotificationChildAdapter(
    private val list: MutableList<NotificationChild>,
    private val context: Context
) :
    RecyclerView.Adapter<NotificationChildAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: NotifRvRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(notificationChild: NotificationChild) {

            binding.notifRvTitle.text = notificationChild.pillName
            binding.notifRvExtraPillCount.text =
                notificationChild.pillCount + " "+context.resources.getString(R.string.tabletka_tx)
            if (notificationChild.pillStatus == BEFORE_MEAL)
                binding.notifRvExtraDesc.text = context.resources.getString(R.string.before_meal)
            else if (notificationChild.pillStatus == AFTER_MEAL)
                binding.notifRvExtraDesc.text = context.resources.getString(R.string.after_meal)

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