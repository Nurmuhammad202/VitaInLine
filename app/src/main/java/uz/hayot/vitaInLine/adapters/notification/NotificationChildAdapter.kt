package uz.hayot.vitaInLine.adapters.notification

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.data.model.notification.NotificationModel
import uz.hayot.vitaInLine.databinding.NotifRvRowBinding
import uz.hayot.vitaInLine.util.Constants.AFTER_MEAL
import uz.hayot.vitaInLine.util.Constants.BEFORE_MEAL
import uz.hayot.vitaInLine.util.Constants.HEALING
import uz.hayot.vitaInLine.util.Constants.RECOMMENDATION

class NotificationChildAdapter(
    private val list: MutableList<NotificationModel>,
    private val context: Context,
    private val status: String
) : RecyclerView.Adapter<NotificationChildAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: NotifRvRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun onBind(notificationChild: NotificationModel) {

            if (status == HEALING) {
                binding.notifRvExtraInfo.visibility = View.VISIBLE
                binding.notifRvIcon.setImageDrawable(context.getDrawable(R.drawable.pill_parasetamol_icon))

                binding.notifRvTitle.text = notificationChild.pillName
                binding.notifRvExtraPillCount.text =
                    notificationChild.pillCount + " " + context.resources.getString(R.string.tabletka_tx)
                if (notificationChild.pillStatus == BEFORE_MEAL) binding.notifRvExtraDesc.text =
                    context.resources.getString(R.string.before_meal)
                else if (notificationChild.pillStatus == AFTER_MEAL) binding.notifRvExtraDesc.text =
                    context.resources.getString(R.string.after_meal)
            } else if (status == RECOMMENDATION) {
                binding.notifRvExtraInfo.visibility = View.GONE
                binding.notifRvTitle.text = notificationChild.pillName
                binding.notifRvIcon.setImageDrawable(context.getDrawable(R.drawable.davliniya_icon))
            }


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