package com.example.fitnessapp.feature.welcome.launch.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.firebase.FirebaseManager
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentLaunchBinding
import com.example.fitnessapp.util.toast.ToastHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LaunchFragment : Fragment() {
    private lateinit var binding: FragmentLaunchBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInResultListener: ActivityResultLauncher<Intent>
    private val TAG = "LaunchFragment: "


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchBinding.inflate(layoutInflater, container, false)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSignInListener()
        binding.launchBtnEmail.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_launchFragment_to_loginFragment)
        }
        binding.launchTvCreateAccount.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_launchFragment_to_registerFragment)
        }

        binding.launchBtnGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            signInResultListener.launch(signInIntent)
        }
    }

    private fun setSignInListener() {
        signInResultListener =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data = result.data
                if (result.resultCode == Activity.RESULT_OK && data != null) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    if (task.isSuccessful) {
                        try {
                            val account = task.getResult(ApiException::class.java)
                            val signInResult = FirebaseManager.googleSignIn(account)
                            signInResult.fold(
                                onSuccess = { _ ->
                                    Log.i(TAG, "GoogleSignIn successful!")
                                    findNavController().navigate(R.id.action_launchFragment_to_initialFragment)
                                },
                                onFailure = { exception ->
                                    Log.e(
                                        TAG,
                                        " ${exception.message.toString()}"
                                    )
                                    ToastHelper.showToast(
                                        requireContext(),
                                        "Google sign in failed!!"
                                    )
                                }
                            )
                        } catch (e: ApiException) {
                            Log.e(TAG, "Google sign in failed with exception: ${e.statusCode}")
                            ToastHelper.showToast(requireContext(), e.message.toString())
                        }
                    } else {
                        Log.e(TAG, task.exception?.message.toString())
                    }
                }
            }
    }
}

