package uz.hayot.vitaInLine.ui.main

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.adapters.DavolanishParentAdapter
import uz.hayot.vitaInLine.adapters.TavsiyanomaParentAdapter
import uz.hayot.vitaInLine.databinding.FragmentTavsiyanomaBinding
import uz.hayot.vitaInLine.fake_data.FakeData
import uz.hayot.vitaInLine.models.ParentRcModel


class TavsiyanomaFragment : Fragment() {
    private var _binding: FragmentTavsiyanomaBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTavsiyanomaBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFakeDataAdapter()

        binding.tavsiyanomaHistory.setOnClickListener {
            showNotificationDialog()
        }

    }

    private fun showNotificationDialog() {
        val dialog = Dialog(binding.root.context)
        dialog.setContentView(R.layout.notification_dialog)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.BottomDialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);


        val exitButton = dialog.findViewById<ImageView>(R.id.notificationDismissIcon)
        exitButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun initFakeDataAdapter() {
        val list: MutableList<ParentRcModel> = FakeData.getParentRcData()
        val adapter = TavsiyanomaParentAdapter(list)
        binding.tavRecyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}