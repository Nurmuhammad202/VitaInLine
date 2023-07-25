package uz.hayot.vitaInLine.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.adapters.notification.NotificationChildAdapter
import uz.hayot.vitaInLine.adapters.subadapter.DavolanishParent
import uz.hayot.vitaInLine.data.local.room.entity.PillModel
import uz.hayot.vitaInLine.data.model.AlarmData
import uz.hayot.vitaInLine.data.model.notification.NotificationModel
import uz.hayot.vitaInLine.databinding.FragmentDavolanishBinding
import uz.hayot.vitaInLine.util.*
import uz.hayot.vitaInLine.util.Constants.HEALING
import uz.hayot.vitaInLine.util.functions.ExtraFunctions.Companion.convertTimeUseDate
import uz.hayot.vitaInLine.util.functions.ExtraFunctions.Companion.getDifferentPillId

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class DavolanishFragment : Fragment() {
    private var _binding: FragmentDavolanishBinding? = null
    private val binding get() = _binding!!
    private val davolanishViewModel: DavolanishViewModel by viewModels()
    private lateinit var dataList: List<PillModel>
    private var isNotification = false
    private var timeNotification = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDavolanishBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            isNotification = it.getBoolean("notification")
            timeNotification = it.getString("time").toString()
        }

        binding.animationDavolanishView.visibility = View.VISIBLE
        davolanishViewModel.getHealing()

        davolanishViewModel.success.observe(requireActivity()) { success ->
            if (success) {
                binding.animationDavolanishView.visibility = View.GONE
                dataList = davolanishViewModel.getHealingData()
                if (dataList.isNotEmpty()) {
                    initDataAdapter(dataList)
                    setAlarmDavolanish(dataList)
                    binding.davolanishNotFoundContainer.visibility = View.GONE
                } else
                    binding.davolanishNotFoundContainer.visibility = View.VISIBLE



                if (isNotification) {
                    if (dataList.isNotEmpty()) {
                        showNotificationDialog(dataList, timeNotification)
                    } else {
                        Toast.makeText(
                            binding.root.context,
                            "${timeNotification},${isNotification}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                davolanishViewModel.savePillDataRoom(dataList)
            } else {
                if (binding.animationDavolanishView.isVisible) {
                    binding.animationDavolanishView.visibility = View.GONE
                    Toast.makeText(
                        binding.root.context,
                        davolanishViewModel.getErrorText(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }


        binding.davolanishHistory.setOnClickListener {
            findNavController().navigate(
                R.id.action_davolanishFragment_to_davolanishHistoryFragment
            )
        }
        binding.davolanishBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun showNotificationDialog(list: List<PillModel>, time: String) {
        val dialog = Dialog(binding.root.context)
        dialog.setContentView(R.layout.notification_dialog)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.BottomDialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);


        val exitButton = dialog.findViewById<ImageView>(R.id.notificationDismissIcon)
        val cancelBtn = dialog.findViewById<ImageView>(R.id.notificationCancelBtn)
        val okBtn = dialog.findViewById<Button>(R.id.notificationOkBtn)
        val skipBtn = dialog.findViewById<Button>(R.id.notificationSkipBtn)
        val timeNotification = dialog.findViewById<TextView>(R.id.notificationPillTime)
        val rvNotification = dialog.findViewById<RecyclerView>(R.id.notificationRv)
        val notifDesc = dialog.findViewById<TextView>(R.id.notificationDesc)
        val notifTitle = dialog.findViewById<TextView>(R.id.notificationContent)

        var _time: String = ""

        notifDesc.text = binding.root.context.getString(R.string.pill_drink_att_text)
        notifTitle.text= binding.root.context.getString(R.string.pills)

        _time = time.ifEmpty { list[0].times?.get(0).toString() }

        timeNotification.text = _time


        rvNotification.adapter =
            NotificationChildAdapter(getTimeData(list, _time), binding.root.context,HEALING)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        okBtn.setOnClickListener {
            dialog.dismiss()
        }

        skipBtn.setOnClickListener {
            setFifteenAfterAlarm(requireContext(), AlarmData(_time, mutableListOf()),HEALING)
            dialog.dismiss()

        }

        exitButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    // adapter init qilish
    private fun initDataAdapter(list: List<PillModel>) {
        val adapter = DavolanishParent(list)
        binding.davRecyclerView.adapter = adapter
        adapter.setOnInfoClicked(object : DavolanishParent.OnParentInfoClickedListener {
            override fun onInfoClicked(position: Int) {
                super.onInfoClicked(position)
                showBottomDialog(list[position])
            }
        })

    }

    // dorilar haqida dialog
    @SuppressLint("SetTextI18n")
    private fun showBottomDialog(dataObject: PillModel) {
        val dialog = Dialog(binding.root.context)
        dialog.setContentView(R.layout.davolanish_info_dialog)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.BottomDialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);

        val pillName = dialog.findViewById<TextView>(R.id.infoDialogPillTitle)
        val pillInformation = dialog.findViewById<TextView>(R.id.infoDialogPillDesc)
        val pillPeriodDate = dialog.findViewById<TextView>(R.id.infoDialogPeriodDate)
        val pillTimes = dialog.findViewById<TextView>(R.id.infoDialogTimeTakingPill)
        val pillChildCount = dialog.findViewById<TextView>(R.id.infoDialogChildPillCount)
        val pillChildStatus = dialog.findViewById<TextView>(R.id.infoDialogPillStatus)
        val pillChildCountDay = dialog.findViewById<TextView>(R.id.infoDialogPillCountDate)
        val infoDialogPillExtraDesc = dialog.findViewById<TextView>(R.id.infoDialogPillExtraDesc)

        pillName.text = dataObject.pill
        pillInformation.text = dataObject.information
        pillPeriodDate.text = dataObject.period.toString()
        infoDialogPillExtraDesc.text=dataObject.extraInformation
        val times = StringBuilder()
        for (i in 0 until dataObject.times?.size!!) {
            times.append(dataObject.times[i])
            if (i != dataObject.times.size - 1) times.append(",")
        }
        pillTimes.text = times.toString()


        pillChildCount.text = "${dataObject.quantity} ${resources.getString(R.string.tabletka_tx)}"

        if (dataObject.type == Constants.BEFORE_MEAL)
            pillChildStatus.text = resources.getString(R.string.before_meal)
        else if (dataObject.type == Constants.AFTER_MEAL)
            pillChildStatus.text = resources.getString(R.string.after_meal)


        pillChildCountDay.text =
            "${dataObject.times.size} ${resources.getString(R.string.mahal_tx)}"

        val exitButton = dialog.findViewById<ImageView>(R.id.infoDialogDismiss)
        exitButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    @SuppressLint("NewApi")
    fun setAlarmDavolanish(list: List<PillModel>) {
        // yangi qo'shilgan pillarni aniqlash
        val newPillList = determineNewPill(list)

        if (newPillList.isNotEmpty()) {

            val alarmPillData = prepareAlarmData(newPillList)

            setAlarm(alarmPillData)

        }

    }
    //data listdan  alarm uchun modelni array ko'rinishida alohida ajratib olish
    @RequiresApi(Build.VERSION_CODES.O)
    private fun prepareAlarmData(newList: List<PillModel>): MutableList<AlarmData> {
        val alarmList: MutableList<AlarmData> = ArrayList()
        for (i in newList.indices) {
            for (j in 0 until newList[i].times!!.size) {
                alarmList.add(
                    AlarmData(
                        newList[i].times!![j],
                        convertTimeUseDate(newList[i].times!![j], newList[i].endedDate.toString())
                    )
                )
            }


        }
        return alarmList

    }


    // berilgan list ko'rinishagi vaqtlarda alarm set qilish
    @RequiresApi(Build.VERSION_CODES.N)
    private fun setAlarm(newPillItems: List<AlarmData>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createNotificationChannel(requireContext())
        }
        for (item in newPillItems) {
            setRepeatAlarm(binding.root.context, item,HEALING)
        }
    }




    // alarm chalingan vaqt bo'yicha datadan shu vaqtga mos bo'lgan dorilarni ajratib olish
    private fun getTimeData(list: List<PillModel>, time: String): MutableList<NotificationModel> {
        val childList: MutableList<NotificationModel> = ArrayList()
        for (i in list.indices) {
            for (j in 0 until (list[i].times?.size ?: 0)) {
                if (time == (list[i].times?.get(j) ?: "")) {
                    childList.add(
                        NotificationModel(
                            list[i].pill.toString(),
                            list[i].quantity.toString(),
                            list[i].type.toString()
                        )
                    )
                }

            }
        }



        return childList
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun determineNewPill(newPillItems: List<PillModel>): List<PillModel> {
        val oldPillItems = davolanishViewModel.getPillDataRoom()
//        Toast.makeText(
//            binding.root.context,
//            getDifferentPillId(oldPillItems, newPillItems).toString(),
//            Toast.LENGTH_LONG
//        ).show()
        return getDifferentPillId(oldPillItems, newPillItems)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}


