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
import uz.hayot.vitaInLine.adapters.NotificationChildAdapter
import uz.hayot.vitaInLine.adapters.subadapter.DavolanishParent
import uz.hayot.vitaInLine.data.local.room.entity.PillModel
import uz.hayot.vitaInLine.data.model.AlarmData
import uz.hayot.vitaInLine.data.model.NotificationChild
import uz.hayot.vitaInLine.databinding.FragmentDavolanishBinding
import uz.hayot.vitaInLine.util.Constants
import uz.hayot.vitaInLine.util.createNotificationChannel
import uz.hayot.vitaInLine.util.setAlarm
import java.time.LocalDate

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

        davolanishViewModel.insertData()
        val list = davolanishViewModel.getRoomData()



        Toast.makeText(binding.root.context, list.toString(), Toast.LENGTH_SHORT).show()


        davolanishViewModel.success.observe(requireActivity()) { success ->
            if (success) {
                binding.animationDavolanishView.visibility = View.GONE
                dataList = davolanishViewModel.getHealingData()
                if (dataList.isEmpty())
                    binding.davolanishNotFoundContainer.visibility = View.VISIBLE
                else binding.davolanishNotFoundContainer.visibility = View.GONE

                initDataAdapter(dataList)
                setAlarmFromDateTimes()

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

        timeNotification.text = time
        rvNotification.adapter =
            NotificationChildAdapter(getTimeData(list, time), binding.root.context)




        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        okBtn.setOnClickListener {
            dialog.dismiss()
        }

        skipBtn.setOnClickListener {
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
        binding.davolanishDate.text = dataList[0].startedDate
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

        pillName.text = dataObject.pill
        pillInformation.text = dataObject.information
        pillPeriodDate.text = dataObject.period.toString()
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


    // berilgan list ko'rinishagi vaqtlarda alarm set qilish
    private fun setAlarmDavolanish(timeList: MutableList<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createNotificationChannel(requireContext())
        }

        for (i in 0 until timeList.size) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setAlarm(requireActivity(), AlarmData(timeList[i], i))
            }
        }
    }

    //data listdan vaqtlarni array ko'rinishida alohida ajratib olish
    private fun separateTime(list: List<PillModel>): MutableList<String> {
        val listTime: MutableList<String> = ArrayList()

        for (i in list.indices) {
            for (j in 0 until (list[i].times?.size ?: 0)) {
                listTime.add(list[i].times?.get(j) ?: "")
            }
        }

        return listTime
    }

    // alarm chalingan vaqt bo'yicha datadan shu vaqtga mos bo'lgan dorilarni ajratib olish
    private fun getTimeData(list: List<PillModel>, time: String): MutableList<NotificationChild> {
        val childList: MutableList<NotificationChild> = ArrayList()
        for (i in list.indices) {
            for (j in 0 until (list[i].times?.size ?: 0)) {
                if (time == (list[i].times?.get(j) ?: "")) {
                    childList.add(
                        NotificationChild(
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


    //  alarmni set qilish uchun tekshrish , agardan kun davomida set qilinmagan bo'lsa
    // alarm set qilinadi, agarda set qilingan bo'lsa set qilinmaydi
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAlarmFromDateTimes() {
        val status = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setAlarmStatus()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        if (status) {
            setAlarmDavolanish(separateTime(dataList))


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                saveAlarm()
            }
        }
    }

    // kun davomida alarm  set qilingan uyoki qilinmaganlik halotini aniqlash
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAlarmStatus(): Boolean {
        val lastDate = davolanishViewModel.getAlarm()
        val day: Int = LocalDate.now().dayOfMonth

        return if (lastDate == 0) {
            true
        } else lastDate.toString() != day.toString()

    }

    // notification qo'yilgan kunni shared preferencga saqlab qo'yish
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveAlarm() {
        davolanishViewModel.saveAlarm(LocalDate.now().dayOfMonth)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}