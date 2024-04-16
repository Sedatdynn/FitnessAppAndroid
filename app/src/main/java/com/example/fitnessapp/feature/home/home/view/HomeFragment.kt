package com.example.fitnessapp.feature.home.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.cache.CacheKeys
import com.example.cache.CacheManager
import com.example.firebase.FirebaseManager
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentHomeBinding
import com.example.fitnessapp.util.toast.ToastHelper
import kotlinx.coroutines.launch

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
            lifecycleScope.launch {
                val result = FirebaseManager.signOut()
                result.fold(
                    onSuccess = { _ ->
                        CacheManager.remove(CacheKeys.TOKEN)
                        performLogout(it)
                    },
                    onFailure = { exception ->
                        val errorMessage = exception.message ?: "An error occurred during sign out."
                        Log.e("Sign out error: ", errorMessage)
                        ToastHelper.showToast(
                            requireContext(),
                            errorMessage
                        )
                    }
                )
            }
        }
    }


    private fun performLogout(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_launchFragment)
    }
}