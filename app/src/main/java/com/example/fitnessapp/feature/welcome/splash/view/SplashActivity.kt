package com.example.fitnessapp.feature.welcome.splash.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cache.CacheManager
import com.example.firebase.FirebaseManager
import com.example.fitnessapp.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        CacheManager.init(applicationContext)
    }
}

