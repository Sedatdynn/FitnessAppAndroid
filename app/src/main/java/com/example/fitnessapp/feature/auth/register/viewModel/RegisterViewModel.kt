package com.example.fitnessapp.feature.auth.register.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private val TAG = "RegisterViewModel"
    private val _registerErrorMessage = MutableLiveData<String>()
    val registerErrorMessage: LiveData<String> get() = _registerErrorMessage

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password
    fun setUsername(username: String) {
        _username.value = username
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun navigateToGender(): Boolean {
        Log.w(TAG, "${_username.value}, ${_email.value}, ${_password.value}")
        return if (!_username.value.isNullOrEmpty() && isEmailValid(
                _email.value ?: ""
            ) && isPasswordValid(
                _password.value ?: ""
            )
        ) {
            true
        } else {
            _registerErrorMessage.postValue("Make sure you fill in the relevant fields correctly!")
            false

        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d).{6,}\$")
        return passwordRegex.matches(password)
    }
}