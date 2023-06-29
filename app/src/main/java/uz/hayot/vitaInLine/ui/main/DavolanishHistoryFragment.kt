package uz.hayot.vitaInLine.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.adapters.DavolanishParentAdapter
import uz.hayot.vitaInLine.data.model.DataItem
import uz.hayot.vitaInLine.databinding.FragmentDavolanishHistoryBinding


@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class DavolanishHistoryFragment : Fragment() {
    private var _binding: FragmentDavolanishHistoryBinding? = null
    private val binding get() = _binding!!

    private val davolanishViewModel: DavolanishViewModel by viewModels()
    private lateinit var dataList: List<DataItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDavolanishHistoryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animationDavolanishHisHomeView.visibility = View.VISIBLE
        davolanishViewModel.getHealingHistory()

        davolanishViewModel.healingDateHistory.observe(requireActivity()) {
            binding.animationDavolanishHisHomeView.visibility = View.GONE
            dataList = it.data as List<DataItem>
            if (dataList.isEmpty()) binding.davHisNotFoundContainer.visibility = View.VISIBLE
            else binding.davHisNotFoundContainer.visibility = View.GONE

            initDataAdapter(dataList)
        }
        binding.davHisBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    private fun initDataAdapter(list: List<DataItem>) {
        val adapter = DavolanishParentAdapter(list, "pill")
        Log.e(TAG, "initDataAdapter: $list")
        binding.davHistoryRv.adapter = adapter
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
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
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
            if (i != dataObject.times.size - 1)
                times.append(",")
        }
        pillTimes.text = times.toString()

        pillChildCount.text = "${dataObject.quantity} ta tabletka"
        pillChildStatus.text = dataObject.type
        pillChildCountDay.text = "${dataObject.times.size}+ mahal"

        val exitButton = dialog.findViewById<ImageView>(R.id.infoDialogDismiss)
        exitButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}