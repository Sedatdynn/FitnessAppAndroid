package com.example.fitnessapp.feature.welcome.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnessapp.R
import com.example.fitnessapp.feature.welcome.launch.view.LaunchActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIMEOUT: Long = 5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            // Yeni aktiviteye geçiş yapılacak intent oluşturuluyor
            val intent = Intent(this, LaunchActivity::class.java)
            // Yeni aktiviteyi başlat
            startActivity(intent)
            // Bu aktiviteyi kapat
            finish()
        }, SPLASH_TIMEOUT)

    }
}