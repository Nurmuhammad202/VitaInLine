package uz.hayot.vitaInLine.ui.auth

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.data.model.CreateDataPatient
import uz.hayot.vitaInLine.databinding.FragmentSignUpBinding
import uz.hayot.vitaInLine.ui.dialog.DataPicterDialog
import uz.hayot.vitaInLine.util.functions.ExtraFunctions

@AndroidEntryPoint
class SignUpFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var resultCalendar: String = ""
    private val authViewModel: AuthViewModel by viewModels()
    private var country = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpannableText()
        initSpinner()
        binding.signUpBirthDate.setOnClickListener {
            showCalendarDialog()
        }
        binding.signUpBtn.setOnClickListener {
            val fio = binding.signUpUsername.text.toString()
            val data = binding.signUpBirthDate.text.toString()
            val passport = binding.signUpSerialNumber.text.toString().replace(" ", "")
            val country = country
            val phone = binding.signUpPhoneNumber.text.toString().replace(" ", "")
            authViewModel.createUser(
                dataPatient = CreateDataPatient(
                    fullname = fio,
                    birthday = data,
                    passport = passport,
                    province = country,
                    phone = phone
                )
            )
        }

        binding.signUpBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        authViewModel.success.observe(requireActivity()) {
            if (it) {
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }
        }

    }

    private lateinit var regionSpinnerAdapter: ArrayAdapter<String>


    private fun initSpannableText() {
        val item = resources.getText(R.string.sign_up_btn_text)
        val startColor: Int = resources.getColor(R.color.secondary_text_color)
        val endColor: Int = resources.getColor(R.color.main_color)
        binding.signUpTitle.text =
            ExtraFunctions.twoColoredText(item.toString(), 0, 10, startColor, endColor)

    }

    private fun initSpinner() {

        regionSpinnerAdapter = ArrayAdapter(
            binding.root.context, R.layout.simple_spinner_item,
            resources.getStringArray(R.array.spinner_region_items)
        )
        binding.signUpRegionSpinner.onItemSelectedListener = this;
        binding.signUpRegionSpinner.setPopupBackgroundResource(R.drawable.edit_text_no_active)
        binding.signUpRegionSpinner.adapter = regionSpinnerAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showCalendarDialog() {
        val dialog = DataPicterDialog(requireContext()){
            binding.signUpBirthDate.text = it
        }
        dialog.show()

//        val dialog = Dialog(binding.root.context)
//        dialog.setContentView(R.layout.calendar_dialog)
//        dialog.window?.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
//        dialog.window?.attributes?.windowAnimations = R.style.BottomDialogAnimation;
//        dialog.window?.setGravity(Gravity.BOTTOM);
//
//        val calendar = dialog.findViewById<CalendarView>(R.id.dialogCalendar)
//        val okBtn = dialog.findViewById<TextView>(R.id.calendarOkBtn)
//        val cancelBtn = dialog.findViewById<TextView>(R.id.calendarCancelBtn)
//
//
//
//        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
//
//            val day = if (dayOfMonth < 10) {
//                "0$dayOfMonth"
//            } else {
//                dayOfMonth.toString()
//            }
//            val monthCustom = if (month + 1 < 10) {
//                "0${month + 1}"
//            } else {
//                { month + 1 }.toString()
//            }
//            resultCalendar = "${day}.${monthCustom}.${year}"
//
//        }
//
//        okBtn.setOnClickListener {
//            binding.signUpBirthDate.text = resultCalendar
//            dialog.dismiss()
//        }
//        cancelBtn.setOnClickListener {
//            dialog.dismiss()
//        }
//        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        country = regionSpinnerAdapter.getItem(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}