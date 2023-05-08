package uz.hayot.vitaInLine.ui.auth

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.databinding.FragmentSignUpBinding
import uz.hayot.vitaInLine.util.functions.ExtraFunctions


class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var resultCalendar:String=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpannableText()
        initSpinner()
        binding.signUpBirthDate.setOnClickListener {
            showCalendarDialog()
        }

    }

    private fun initSpannableText() {
        val item = resources.getText(R.string.sign_up_btn_text)
        val startColor: Int = resources.getColor(R.color.secondary_text_color)
        val endColor: Int = resources.getColor(R.color.main_color)
        binding.signUpTitle.text =
            ExtraFunctions.twoColoredText(item.toString(), 0, 10, startColor, endColor)

    }

    private fun initSpinner() {
        val regionSpinnerAdapter = ArrayAdapter(
            binding.root.context, R.layout.simple_spinner_item,
            resources.getStringArray(R.array.spinner_region_items)
        )
        binding.signUpRegionSpinner.setPopupBackgroundResource(R.drawable.edit_text_background)
        binding.signUpRegionSpinner.adapter = regionSpinnerAdapter
    }

    private fun showCalendarDialog() {
        val dialog = Dialog(binding.root.context)
        dialog.setContentView(R.layout.calendar_dialog)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.window?.attributes?.windowAnimations = R.style.BottomDialogAnimation;
        dialog.window?.setGravity(Gravity.BOTTOM);

        val calendar = dialog.findViewById<CalendarView>(R.id.dialogCalendar)
        val okBtn = dialog.findViewById<TextView>(R.id.okBtn)
        val cancelBtn = dialog.findViewById<TextView>(R.id.cancelBtn)



        calendar.setOnDateChangeListener { _, year, month, dayOfMonth -> resultCalendar ="  ${dayOfMonth}.${month+1}.${year}"}

        okBtn.setOnClickListener {

            binding.signUpBirthDate.text = resultCalendar
            dialog.dismiss()
        }
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}