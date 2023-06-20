package uz.hayot.vitaInLine.ui.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.MainActivity
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.databinding.FragmentHomeBinding
import uz.hayot.vitaInLine.ui.splash.SplashActivity
import uz.hayot.vitaInLine.ui.splash.SplashFragment
import uz.hayot.vitaInLine.util.Localization.changeLan
import uz.hayot.vitaInLine.util.setAlarm

@AndroidEntryPoint
class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animationHomeView.visibility = View.VISIBLE
        mainViewModel.getSignUser()
        initSpinner()

        // setAlarm(5, requireActivity())

        mainViewModel.userResponse.observe(requireActivity()) { userResponse ->
            binding.animationHomeView.visibility = View.GONE
            binding.homeUsername.text = userResponse.data?.fullname
            binding.homeUserBirthDay.text = userResponse.data?.birthday
            binding.homeUserRegion.text = userResponse.data?.province
            binding.homeUserJobPlace.text = userResponse.data?.workplace ?: "Promo Technology"
        }

        binding.homeLogOut.setOnClickListener {
            mainViewModel.logOutPatient()
            val intent = Intent(requireActivity(), SplashActivity::class.java)
            requireActivity().finishAffinity()
            startActivity(intent)

        }

        binding.davolanishBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_davolanishFragment)
        }

        binding.tavsiyanomaBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tavsiyanomaFragment)

        }
        binding.homePillBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pillsFragment)
        }


    }

    private lateinit var languageSpinnerAdapter: ArrayAdapter<String>
    private fun initSpinner() {
        val lang = mainViewModel.getLang()
        languageSpinnerAdapter = if (lang == "ru") {
            ArrayAdapter(
                binding.root.context, R.layout.simple_spinner_item,
                resources.getStringArray(R.array.spinner_items_ru)
            )
        } else {
            ArrayAdapter(
                binding.root.context, R.layout.simple_spinner_item,
                resources.getStringArray(R.array.spinner_items)
            )
        }
        binding.homeLanguageSpinner.setPopupBackgroundResource(R.drawable.edit_text_no_active);
        binding.homeLanguageSpinner.adapter = languageSpinnerAdapter
        binding.homeLanguageSpinner.onItemSelectedListener = this
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        count = 0
    }

    override fun onStop() {
        super.onStop()
        count = 0
    }

    var count = 0
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        Log.e(TAG, "onItemSelectedewwew $count")
        if (count > 0) {
            val lang = languageSpinnerAdapter.getItem(position).toString()
            mainViewModel.saveLang(lang)
            changeLan(lang, requireActivity())
            val intent = Intent(requireActivity(), MainActivity::class.java)
            requireActivity().finishAffinity()
            startActivity(intent)
        }

        count++
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}