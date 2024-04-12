package com.example.fitnessapp.feature.auth.birth_date.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.fitnessapp.databinding.FragmentBirthDateBinding
import com.example.fitnessapp.feature.auth.register.model.UserModel
import java.util.Calendar

class BirthDateFragment : Fragment() {
    private lateinit var binding: FragmentBirthDateBinding
    private val args: BirthDateFragmentArgs by navArgs()
    private lateinit var user: UserModel
    val TAG = "BIRTH DATE FRAGMENT"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBirthDateBinding.inflate(layoutInflater, container, false)
        user = args.userInfo
        user = user.copy(birthYear = 1998)//set first selected value
        Log.i(
            TAG,
            user.toString()
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNumberPicker()
        navigate()
    }

    private fun navigate() {
        binding.birthBtnNext.setOnClickListener {
            Log.i("SELECTED YEAR: ", user.birthYear.toString())
            val action =
                BirthDateFragmentDirections.actionBirthDateFragmentToMobilityFragment(user)
            Navigation.findNavController(it)
                .navigate(action)
        }
    }

    private fun configureNumberPicker() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        binding.birthNumberPicker.apply {
            minValue = currentYear - 60
            maxValue = currentYear - 18
            value = 1998
            setOnValueChangedListener { _, _, newVal ->
                user = user.copy(birthYear = newVal)
                Log.d("SELECTED VALUE: ", newVal.toString())
            }
        }
    }
}