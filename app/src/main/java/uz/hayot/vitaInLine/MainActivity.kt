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
import androidx.appcompat.app.AppCompatDelegate

import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.ui.main.HomeViewModel
import uz.hayot.vitaInLine.util.Localization.changeLan

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        changeLan(mainViewModel.getLang(), this)



    }
}