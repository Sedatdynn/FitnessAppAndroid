package com.example.fitnessapp.feature.auth.forgot_password.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.FirebaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {
    val TAG = "ForgotPasswordViewModel: "
    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private var _isCompleted = MutableLiveData<Boolean>(false)
    val isCompleted: LiveData<Boolean> get() = _isCompleted
    fun setEmail(email: String) {
        _email.value = email
    }

    fun checkVerifyEmail() {
        _isLoading.postValue(true)
        if (isEmailValid(_email.value?.trim().toString())) {
            viewModelScope.launch(Dispatchers.IO) {
                // send request to firebase reset password func
                val (success, errorMessage) = FirebaseManager.resetPassword(_email.value!!)
                // if reset is success (means reset email sent successfully)
                if (success) {
                    _isLoading.postValue(false)
                    _isCompleted.postValue(true)
                } else {
                    // catching error while trying to sent reset email!!
                    errorMessage?.let {
                        Log.e(TAG, it)
                        _errorMessage.postValue(it)
                    }
                    _isCompleted.postValue(false)
                    _isLoading.postValue(false)
                }
            }
        } else {
            // when user's email invalid!!
            _errorMessage.postValue("Please enter a valid email..")
            _isLoading.postValue(false)
            _isCompleted.postValue(false)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }
}
