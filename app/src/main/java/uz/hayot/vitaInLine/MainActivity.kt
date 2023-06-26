package uz.hayot.vitaInLine

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi

import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.ui.main.HomeViewModel
import uz.hayot.vitaInLine.util.Localization.changeLan

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: HomeViewModel by viewModels()
    private lateinit var requestLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, "onCreate: ${mainViewModel.getLang()}")
        changeLan(mainViewModel.getLang(), this)


        requestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {


            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    askForNotificationPermission()
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun askForNotificationPermission() {
        requestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}