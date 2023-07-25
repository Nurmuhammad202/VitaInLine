package uz.hayot.vitaInLine.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.MainActivity
import uz.hayot.vitaInLine.R
import uz.hayot.vitaInLine.databinding.FragmentHomeBinding
import uz.hayot.vitaInLine.ui.splash.SplashActivity
import uz.hayot.vitaInLine.util.Constants.REQUEST_LOCATION
import uz.hayot.vitaInLine.util.Localization.changeLan
import uz.hayot.vitaInLine.util.functions.ExtraFunctions.Companion.convertShortDesc


@AndroidEntryPoint
class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @SuppressLint("InlinedApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bildirishnomaga ruxsat berilgan yoki yo'qligi tekshirish
        checkNotificationPermission()
        // ma'lumotlar kelgunga animatsiya
        binding.animationHomeView.visibility = View.VISIBLE
        // ma'lumotlar olib kelish uchun so'rov jo'natish
        mainViewModel.getSignUser()
        // tilni amashtirish uchun til spinnerini init qilish
        initSpinner()

        //viewModeldan success ni observe qilish

        binding.btnDoctor.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragment_doctor)
        }

        binding.call.setOnClickListener {
            try {
                val telegramIntent = Intent(Intent.ACTION_VIEW)
                telegramIntent.data = Uri.parse("https://telegram.me/ZEALvitainline")
                startActivity(telegramIntent)
            } catch (e: Exception) {
                // show error message
            }
        }

        mainViewModel.success.observe(requireActivity()) { success ->
            if (success) {
                val userData = mainViewModel.getHomeUserData()
                binding.animationHomeView.visibility = View.GONE
                binding.homeUserBirthDay.text = userData.data?.birthday
                binding.homeUserRegion.text = userData.data?.province
                binding.homeUserJobPlace.text = userData.data?.workplace ?: "Promo Technology"
                val name = userData.data?.fullname
                if (name?.length!! > 26) {
                    binding.homeUsername.text = convertShortDesc(name, 0, 23)
                } else {
                    binding.homeUsername.text = name
                }

            } else {
                if (binding.animationHomeView.isVisible) {
                    binding.animationHomeView.visibility = View.GONE
                    Toast.makeText(
                        binding.root.context,
                        mainViewModel.getErrorText(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }


        // log out tugmasi bosilishi
        binding.homeLogOut.setOnClickListener {
            mainViewModel.deleteRoomData()
            mainViewModel.logOutPatient()
            val intent = Intent(requireActivity(), SplashActivity::class.java)
            requireActivity().finishAffinity()
            startActivity(intent)
        }


        // davolanish oynasiga o'tish
        binding.davolanishBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_davolanishFragment)
        }
        //tavsiyanoma oynasiga o'tish

        binding.tavsiyanomaBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tavsiyanomaFragment)

        }

        // dorilar oynasiga o'tish
        binding.homePillBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pillsFragment)
        }


    }

    private lateinit var languageSpinnerAdapter: ArrayAdapter<String>
    private fun initSpinner() {
        val lang = mainViewModel.getLang()
        languageSpinnerAdapter = if (lang == "ru") {
            ArrayAdapter(
                binding.root.context,
                R.layout.simple_spinner_item,
                resources.getStringArray(R.array.spinner_items_ru)
            )
        } else {
            ArrayAdapter(
                binding.root.context,
                R.layout.simple_spinner_item,
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


    private fun checkNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS,
                    ), REQUEST_LOCATION
                )
            }
        }
    }


}