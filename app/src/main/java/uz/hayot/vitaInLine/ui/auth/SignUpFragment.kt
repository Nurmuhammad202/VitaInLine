package uz.hayot.vitaInLine.ui.auth

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
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

            if (fio != "" && data != "" && passport != "" && country != "" && phone != "") {
                val errorText = validateSignUpItems(fio, data, passport, phone)
                if (errorText == "") {
                    binding.animationSignUpView.visibility = View.VISIBLE
                    authViewModel.createUser(
                        dataPatient = CreateDataPatient(
                            fullname = fio,
                            birthday = data,
                            passport = passport,
                            province = country,
                            phone = phone
                        )
                    )
                } else Toast.makeText(binding.root.context, errorText, Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(binding.root.context, R.string.malumotlar, Toast.LENGTH_SHORT).show()
            }


        }

        binding.signUpBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        authViewModel.success.observe(requireActivity()) {
            if (it) {
                Toast.makeText(
                    binding.root.context,
                    R.string.success_sign_up,
                    Toast.LENGTH_SHORT
                ).show()
                binding.animationSignUpView.visibility = View.GONE
                findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            } else {
                if (binding.animationSignUpView.isVisible) {
                    binding.animationSignUpView.visibility = View.GONE
                    Toast.makeText(
                        binding.root.context,
                        authViewModel.getErrorText(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
        val dialog = DataPicterDialog(requireContext()) {
            binding.signUpBirthDate.text = it
        }
        dialog.show()


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


    // validate sign up items

    private fun validateSignUpItems(
        name: String,
        date: String,
        passport: String,
        phoneNumber: String
    ): String {
        return if (name.length < 2)
            resources.getString(R.string.name_error)
        else if (!validateDate(date))
            resources.getString(R.string.birthday_error)
        else if (passport.contains("_"))
            resources.getString(R.string.passport_error)
        else if (!validatePassword(passport))
            resources.getString(R.string.passport_error_two)
        else if (phoneNumber.contains("_"))
            resources.getString(R.string.phone_number_error)
        else if (!validatePhoneNumber(phoneNumber))
            resources.getString(R.string.phone_number_error_two)
        else ""

    }

    private fun validateDate(date: String): Boolean {
        val regex = Regex("\\d{2}\\.\\d{2}\\.\\d{4}")
        return date.matches(regex)
    }

    private fun validatePassword(passport: String): Boolean {
        val regex = Regex("[A-Z]{2}\\d{7}")
        return passport.matches(regex)
    }

    private fun validatePhoneNumber(number: String): Boolean {
        val index = number.substring(4, 6)
        return (number.substring(
            0,
            4
        ) == "+998" && (index == "50" || index == "99" || index == "33" || index == "97" ||
                index == "93" || index == "88" || index == "94" || index == "98" ||
                index == "55" || index == "95" || index == "71" || index == "70" ||
                index == "67" || index == "72" || index == "66" || index == "73" ||
                index == "69" || index == "74" || index == "75" || index == "76" ||
                index == "65" || index == "79" || index == "62" || index == "61" ||
                index == "77" || index == "90" || index == "91"))
    }


}