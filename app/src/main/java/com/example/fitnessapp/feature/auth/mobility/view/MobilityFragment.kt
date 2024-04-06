package com.example.fitnessapp.feature.auth.mobility.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentMobilityBinding


class MobilityFragment : Fragment() {
    private lateinit var binding: FragmentMobilityBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMobilityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ArrayAdapter.createFromResource(
            view.context,
            R.array.mobility_items,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.mobilitySpinner.adapter = adapter
        }
        binding.mobilitySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    pos: Int,
                    id: Long
                ) {
                    //logged selected menu item!
                    Log.d("Spinner selected item: ", parent.getItemAtPosition(pos).toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }


    }


}