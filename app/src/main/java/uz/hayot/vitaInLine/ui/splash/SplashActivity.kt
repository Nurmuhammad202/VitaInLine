package uz.hayot.vitaInLine.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.AndroidEntryPoint
import uz.hayot.vitaInLine.R

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_splash)
    }
}