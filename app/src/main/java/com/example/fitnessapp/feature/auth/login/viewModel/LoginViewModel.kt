package com.example.fitnessapp.feature.auth.login.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.AuthResult
import com.example.firebase.FirebaseManager
import com.example.fitnessapp.feature.auth.login.model.LoginModel
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(), ILoginViewModel {
    val TAG = "LoginViewModel"

    private val viewModel: MutableLiveData<LoginModel> by lazy {
        MutableLiveData<LoginModel>()
    }
    private val _loginErrorMessage = MutableLiveData<String>()
    val loginErrorMessage: LiveData<String> get() = _loginErrorMessage

    private val _navigateToHomeLiveData = MutableLiveData<Boolean>()
    val navigateToHomeLiveData: LiveData<Boolean> get() = _navigateToHomeLiveData


    override fun signIn() {
        val email: String? = viewModel.value?.email
        val password: String? = viewModel.value?.password
        Log.i(TAG, "email: $email password: $password")
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            viewModelScope.launch {
                when (val result = FirebaseManager.signIn(email, password)) {
                    is AuthResult.Success -> {
                        val user = result.user
                        Log.i(TAG, "USER email: ${user.email}")
                        _navigateToHomeLiveData.postValue(true)
                    }

                    is AuthResult.Error -> {
                        _loginErrorMessage.postValue(result.errorMessage)
                    }
                }
            }
        } else {
            _loginErrorMessage.postValue("Fill all fields!!")
        }
    }

    override fun setEmail(email: String) {
        val currentModel = viewModel.value ?: LoginModel()
        currentModel.email = email
        viewModel.value = currentModel
    }

    override fun setPassword(password: String) {
        val currentModel = viewModel.value ?: LoginModel()
        currentModel.password = password
        viewModel.value = currentModel
    }
}