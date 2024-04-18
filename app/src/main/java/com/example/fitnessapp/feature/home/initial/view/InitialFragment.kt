package com.example.fitnessapp.feature.home.initial.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentInitialBinding
import com.example.fitnessapp.feature.home.home.view.HomeFragment
import com.example.fitnessapp.feature.home.profile.view.ProfileFragment
import com.example.fitnessapp.feature.welcome.launch.view.LaunchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class InitialFragment : Fragment() {
    private lateinit var binding: FragmentInitialBinding
    private lateinit var bottomNavView: BottomNavigationView
    val TAG = "Initial Fragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInitialBinding.inflate(inflater, container, false)
        bottomNavView = binding.homeNavigationBar
        setBottomNavigationBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Geri tuşuna basıldığında burası çalışır
            lifecycleScope.launch {
                Log.i(TAG, "Go BACK clicked!!!")
                // Geri tuşuna basıldığında fragment stack'ini temizle ve uygulamadan çık
                findNavController().popBackStack(R.id.homeFragment, false)
                requireActivity().finish()


            }
        }
    }

    private fun setBottomNavigationBar() {
        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    Log.i(TAG, "Home clicked!!")
                    replaceFragment(HomeFragment())
                }

                R.id.profileFragment -> {
                    Log.i(TAG, "Profile clicked!!")
                    replaceFragment(ProfileFragment())
                }

                else -> {
                    //todo: Create error fragment for project!!
                    Log.e(TAG, "navigation couldn't find!")
                    replaceFragment(LaunchFragment())
                }
            }
            true
        }
        bottomNavView.selectedItemId = R.id.homeFragment
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


}