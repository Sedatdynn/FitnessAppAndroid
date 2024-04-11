package com.example.fitnessapp.feature.home.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.firebase.FirebaseManager
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentHomeBinding
import com.example.fitnessapp.util.toast.ToastHelper

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSignOutClick()
    }

    private fun onSignOutClick() {
        binding.homeBtnOut.setOnClickListener {

            FirebaseManager.signOut { errorMessage ->
                if (errorMessage != null) {
                    ToastHelper.showToast(
                        it.context,
                        errorMessage.toString()
                    )
                } else {
                    performLogout(it)
                }
            }
        }
    }

    private fun performLogout(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment)
    }
}