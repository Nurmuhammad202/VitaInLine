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
import uz.hayot.vitaInLine.adapters.ParentRecyclerAdapter
import uz.hayot.vitaInLine.databinding.FragmentDavolanishBinding
import uz.hayot.vitaInLine.fake_data.FakeData
import uz.hayot.vitaInLine.models.ParentRcModel


class DavolanishFragment : Fragment() {
    private var _binding: FragmentDavolanishBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDavolanishBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFakeDataAdapter()

        binding.davolanishHistory.setOnClickListener {
            showBottomDialog()
        }

    }

    private fun initFakeDataAdapter() {
        var list: MutableList<ParentRcModel> = ArrayList()
        list = FakeData.getParentRcData()
        val adapter = ParentRecyclerAdapter(list)
        binding.dRecyclerView.adapter = adapter
    }

    private fun showBottomDialog() {
        val dialog = Dialog(binding.root.context)
        dialog.setContentView(R.layout.description_bottom_layout)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.BottomDialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);


        val exitButton = dialog.findViewById<ImageView>(R.id.bottomDialogDismiss)
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