package com.example.fitnessapp.feature.auth.register.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.register_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.registerFragment)
    }
}