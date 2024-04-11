package com.example.fitnessapp.feature.welcome.splash.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.firebase.FirebaseManager
import com.example.fitnessapp.R


class SplashFragment : Fragment() {
    val TAG = "SplashFragment"
    private val SPLASH_TIMEOUT: Long = 3000
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        checkUser()
        return view
    }

    private fun checkUser() {
        val currentUser = FirebaseManager.currentUser
        if (currentUser != null) {
            Log.i(TAG, "currentUser: $currentUser")
            navigate(R.id.action_splashFragment_to_homeFragment)
        } else {
            Log.i(TAG, "currentUser is null")
            navigate(R.id.action_splashFragment_to_launchFragment)
        }
    }

    private fun navigate(resId: Int) {
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(
                resId,
                null,
                NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build()
            )
        }, SPLASH_TIMEOUT)
    }
}