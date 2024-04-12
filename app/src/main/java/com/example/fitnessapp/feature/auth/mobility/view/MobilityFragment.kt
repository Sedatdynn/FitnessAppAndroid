package com.example.fitnessapp.feature.auth.mobility.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentMobilityBinding
import com.example.model.UserModel


class MobilityFragment : Fragment() {
    private lateinit var binding: FragmentMobilityBinding
    private val args: MobilityFragmentArgs by navArgs()
    private lateinit var user: UserModel
    val TAG = "MOBILITY FRAGMENT"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMobilityBinding.inflate(inflater, container, false)
        user = args.userInfo
        user = user.copy(mobility = "Desk job or sedentary")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.mobility_items,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.mobilitySpinner.adapter = adapter
        }

        binding.mobilitySpinner.onItemSelectedListener =
            createItemSelectedListener()

        onNextClick()
    }

    private fun createItemSelectedListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                pos: Int,
                id: Long
            ) {
                logSelectedItem(parent, pos)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun logSelectedItem(parent: AdapterView<*>, pos: Int) {
        val selectedItem = parent.getItemAtPosition(pos).toString()
        user = user.copy(mobility = selectedItem)//set first selected value
        Log.d("Spinner selected item: ", selectedItem)
    }

    private fun onNextClick() {
        binding.mobilityBtnNext.setOnClickListener {
            Log.i(
                TAG,
                user.toString()
            )
            val action =
                MobilityFragmentDirections.actionMobilityFragmentToHeightFragment(user)
            Navigation.findNavController(it)
                .navigate(action)
        }
    }
}
