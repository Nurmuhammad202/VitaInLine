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
import uz.hayot.vitaInLine.adapters.DavolanishParentAdapter
import uz.hayot.vitaInLine.adapters.notification.NotificationChildAdapter
import uz.hayot.vitaInLine.data.local.room.entity.RecommendationModel
import uz.hayot.vitaInLine.data.model.AlarmData
import uz.hayot.vitaInLine.data.model.notification.NotificationModel
import uz.hayot.vitaInLine.databinding.FragmentTavsiyanomaBinding
import uz.hayot.vitaInLine.util.Constants
import uz.hayot.vitaInLine.util.createNotificationChannel
import uz.hayot.vitaInLine.util.functions.ExtraFunctions
import uz.hayot.vitaInLine.util.setFifteenAfterAlarm
import uz.hayot.vitaInLine.util.setRepeatAlarm


@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class TavsiyanomaFragment : Fragment() {
    private var _binding: FragmentTavsiyanomaBinding? = null
    private val binding get() = _binding!!
    private val davolanishViewModel: DavolanishViewModel by viewModels()
    private lateinit var dataList: List<RecommendationModel>
    private var isNotification = false
    private var timeNotification = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTavsiyanomaBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animationTavsiyaView.visibility = View.VISIBLE
        davolanishViewModel.recommendations()

        arguments?.let {
            isNotification = it.getBoolean("notification")
            timeNotification = it.getString("time").toString()
        }


        davolanishViewModel.success.observe(requireActivity()) { success ->
            if (success) {
                binding.animationTavsiyaView.visibility = View.GONE
                dataList = davolanishViewModel.getRecommendationData()
                if (dataList.isEmpty()) binding.tavsiyaNotFoundContainer.visibility = View.VISIBLE
                else binding.tavsiyaNotFoundContainer.visibility = View.GONE

                if (dataList.isNotEmpty()) {
                    initDataAdapter(dataList)
                    setAlarmTavsiya(dataList)
                    binding.tavsiyaNotFoundContainer.visibility = View.GONE
                } else {
                    binding.tavsiyaNotFoundContainer.visibility = View.VISIBLE
                }


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

                davolanishViewModel.saveRecommendationDataRoom(dataList)



            } else {
                if (binding.animationTavsiyaView.isVisible) {
                    binding.animationTavsiyaView.visibility = View.GONE
                    Toast.makeText(
                        binding.root.context,
                        davolanishViewModel.getErrorText(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        binding.tavsiyanomaHistory.setOnClickListener {
            findNavController().navigate(R.id.action_tavsiyanomaFragment_to_tavsiyanomaHistoryFragment)

        }

        binding.tavsiyanomaBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun setAlarmTavsiya(dataList: List<RecommendationModel>) {
        // yangi qo'shilgan recommendation ni aniqlash
        val newPillList = determineNewRecommendation(dataList)
        if (newPillList.isNotEmpty()) {
            val alarmPillData = prepareAlarmData(newPillList)
            setAlarm(alarmPillData)

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setAlarm(alarmPillData: MutableList<AlarmData>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createNotificationChannel(requireContext())
        }
        for (item in alarmPillData) {
            setRepeatAlarm(binding.root.context, item, Constants.RECOMMENDATION)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun prepareAlarmData(newRecommendationList: List<RecommendationModel>):MutableList<AlarmData> {
        val alarmList: MutableList<AlarmData> = ArrayList()
        for (i in newRecommendationList.indices) {
            for (j in 0 until newRecommendationList[i].times!!.size) {
                alarmList.add(
                    AlarmData(
                        newRecommendationList[i].times!![j],
                        ExtraFunctions.convertTimeUseDate(
                            newRecommendationList[i].times!![j],
                            newRecommendationList[i].endedDate.toString()
                        )
                    )
                )
            }


        }
        return alarmList
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun determineNewRecommendation(newPillItems: List<RecommendationModel>): List<RecommendationModel> {
        val oldPillItems = davolanishViewModel.getRecommendationDataRoom()
        return ExtraFunctions.getDifferentRecommadationId(oldPillItems, newPillItems)
    }


    private fun initDataAdapter(list: List<RecommendationModel>) {
        val adapter = DavolanishParentAdapter(list, "recommendations")
        binding.tavsiyanomaDate.text = dataList[0].startedDate
        binding.tavRecyclerView.adapter = adapter
        adapter.setOnInfoClicked(object : DavolanishParentAdapter.OnParentInfoClickedListener {

        })

    }


    private fun showNotificationDialog(list: List<RecommendationModel>, time: String) {
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

        notifDesc.text = binding.root.context.getString(R.string.tavsiya_att_text)
        notifTitle.text = binding.root.context.getString(R.string.tavsiya_notif_title)

        _time = time.ifEmpty { list[0].times?.get(0).toString() }

        timeNotification.text = _time


        rvNotification.adapter =
            NotificationChildAdapter(
                getTimeData(list, _time), binding.root.context,
                Constants.RECOMMENDATION
            )

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        okBtn.setOnClickListener {
            dialog.dismiss()
        }

        skipBtn.setOnClickListener {
            setFifteenAfterAlarm(
                requireContext(), AlarmData(_time, mutableListOf()),
                Constants.RECOMMENDATION
            )
            dialog.dismiss()

        }

        exitButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun getTimeData(
        list: List<RecommendationModel>,
        time: String
    ): MutableList<NotificationModel> {
        val childList: MutableList<NotificationModel> = ArrayList()
        for (i in list.indices) {
            for (j in 0 until (list[i].times?.size ?: 0)) {
                if (time == (list[i].times?.get(j) ?: "")) {
                    childList.add(
                        NotificationModel(
                            list[i].title.toString(),
                            "",
                            ""
                        )
                    )
                }

            }
        }
        return childList
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}