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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.adapters.DavolanishParentAdapter
import uz.hayot.vitaInLine.adapters.NotificationChildAdapter
import uz.hayot.vitaInLine.data.model.AlarmData
import uz.hayot.vitaInLine.data.model.DataItem
import uz.hayot.vitaInLine.data.model.NotificationChild
import uz.hayot.vitaInLine.databinding.FragmentDavolanishBinding
import uz.hayot.vitaInLine.util.createNotificationChannel
import uz.hayot.vitaInLine.util.functions.ExtraFunctions
import uz.hayot.vitaInLine.util.setAlarm
import java.time.LocalDate
import kotlin.math.abs

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class DavolanishFragment : Fragment() {
    private var _binding: FragmentDavolanishBinding? = null
    private val binding get() = _binding!!
    private val davolanishViewModel: DavolanishViewModel by viewModels()
    private lateinit var dataList: List<DataItem>
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



        davolanishViewModel.healingDate.observe(requireActivity()) {

            binding.animationDavolanishView.visibility = View.GONE
            dataList = it?.data as List<DataItem>
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

    private fun showNotificationDialog(list: List<DataItem>, time: String) {
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
        rvNotification.adapter = NotificationChildAdapter(getTimeData(list, time))




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


    private fun initDataAdapter(list: List<DataItem>) {
        val adapter = DavolanishParentAdapter(list, "pill")
        binding.davolanishDate.text = dataList[0].startedDate
        binding.davRecyclerView.adapter = adapter
        adapter.setOnInfoClicked(object : DavolanishParentAdapter.OnParentInfoClickedListener {
            override fun onInfoClicked(position: Int) {
                super.onInfoClicked(position)
                showBottomDialog(list[position])
            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun showBottomDialog(dataObject: DataItem) {
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

        pillChildCount.text = "${dataObject.quantity} ta tabletka"
        pillChildStatus.text = dataObject.type
        pillChildCountDay.text = "${dataObject.times.size} mahal"

        val exitButton = dialog.findViewById<ImageView>(R.id.infoDialogDismiss)
        exitButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAlarm(timeList: MutableList<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createNotificationChannel(requireContext())
        }
        for (i in 0 until timeList.size) {
            setAlarm(requireActivity(), AlarmData(timeList[i], i))
        }

    }


    private fun separateTime(list: List<DataItem>): MutableList<String> {
        val listTime: MutableList<String> = ArrayList()

        for (i in list.indices) {
            for (j in 0 until (list[i].times?.size ?: 0)) {
                listTime.add(list[i].times?.get(j) ?: "")
            }
        }

        return listTime
    }

    private fun getTimeData(list: List<DataItem>, time: String): MutableList<NotificationChild> {
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAlarmFromDateTimes() {
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setAlarmStatus()
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        ) {
            setAlarm(separateTime(dataList))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                saveAlarm()
            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAlarmStatus(): Boolean {
        val lastDate = davolanishViewModel.getAlarm()
        val day: Int = ExtraFunctions.checkDayFormat(LocalDate.now().dayOfMonth)
        return if (lastDate == 0) true
        else abs(lastDate - day) >= 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveAlarm() {
        davolanishViewModel.saveAlarm(ExtraFunctions.checkDayFormat(LocalDate.now().dayOfMonth))
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}