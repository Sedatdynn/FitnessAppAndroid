package com.example.fitnessapp.feature.home.profile.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cache.CacheKeys
import com.example.cache.CacheManager
import com.example.firebase.FirebaseManager
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentProfileBinding
import com.example.fitnessapp.feature.home.profile.ProfileAdapter
import com.example.fitnessapp.feature.home.profile.model.ProfileItemModel
import com.example.fitnessapp.util.enums.ProfileItemEnum
import com.example.fitnessapp.util.toast.ToastHelper
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileAdapter: ProfileAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    private val items = listOf(
        ProfileItemModel(1, R.drawable.ic_email, "Email", "dayan.sedat1905@gmail.com"),
        ProfileItemModel(2, R.drawable.ic_theme, "Theme Light", "Change theme mode"),
        ProfileItemModel(3, R.drawable.ic_bmi, "BMI", "See BMI result"),
        ProfileItemModel(4, R.drawable.ic_profile, "Update Profile", "Update your information"),
        ProfileItemModel(5, R.drawable.ic_logout, "LogOut", "Have a good day")
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileAdapter = ProfileAdapter(
            requireContext(), items
        )
        binding.profileRecyclerView.adapter = profileAdapter
        binding.profileRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        onCategoryClick(view)
    }

    private fun onCategoryClick(view: View) {
        profileAdapter.onItemClick = { option ->
            Log.e("PROFILE>>>>", option.toString())
            when (option) {
                ProfileItemEnum.LogOut.value -> logOut()

                ProfileItemEnum.Theme.value -> Log.i(
                    "PROFILE: ",
                    "- $option ----> ThemeLight, ${ProfileItemEnum.Theme.value}"
                )

                ProfileItemEnum.BMI.value -> Log.i(
                    "PROFILE: ",
                    "- $option ----> BMI, ${ProfileItemEnum.BMI.value}"
                )

                ProfileItemEnum.Update.value -> Log.i(
                    "PROFILE: ",
                    "- $option ----> UpdateProfile, ${ProfileItemEnum.Update.value}"
                )

                else -> Log.i(
                    "PROFILE: ",
                    "ELSE,- $option  "
                )

            }

        }
    }

    private fun logOut() {
        lifecycleScope.launch {
            val result = FirebaseManager.signOut()
            result.fold(
                onSuccess = { _ ->
                    CacheManager.remove(CacheKeys.TOKEN)
                    performLogout()
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

    private fun performLogout() {
        findNavController()
            .navigate(
                R.id.action_initialFragment_to_launchFragment, null,
                NavOptions.Builder().setPopUpTo(R.id.initialFragment, true).build()
            )
    }
}