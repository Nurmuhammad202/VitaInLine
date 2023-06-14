package uz.hayot.vitaInLine

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels

import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.ui.main.HomeViewModel
import uz.hayot.vitaInLine.util.Localization.changeLan

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, "onCreate: ${mainViewModel.getLang()}")
        changeLan(mainViewModel.getLang(), this)
    }
}