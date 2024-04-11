package com.example.fitnessapp.feature.auth.register.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessapp.feature.auth.login.model.LoginModel
import com.example.fitnessapp.feature.auth.register.model.UserModel

class RegisterViewModel : ViewModel() {
    val TAG = "RegisterViewModel"

    private val viewModel: MutableLiveData<UserModel> by lazy {
        MutableLiveData<UserModel>()
    }

    private val _registerErrorMessage = MutableLiveData<String>()
    val registerErrorMessage: LiveData<String> get() = _registerErrorMessage


    private val _navigateToGenderLiveData = MutableLiveData<Boolean>(false)

    val navigateGenderLiveData: LiveData<Boolean> get() = _navigateToGenderLiveData
    fun setUsername(username: String) {
        val currentModel = viewModel.value ?: UserModel()
        currentModel.username = username
        viewModel.value = currentModel
    }

    fun setEmail(email: String) {
        val currentModel = viewModel.value ?: UserModel()
        currentModel.email = email
        viewModel.value = currentModel
    }

    fun setPassword(password: String) {
        val currentModel = viewModel.value ?: UserModel()
        currentModel.password = password
        viewModel.value = currentModel
    }

    fun navigateToGender() {
        val username: String? = viewModel.value?.username
        val email: String? = viewModel.value?.email
        val password: String? = viewModel.value?.password
        if (!username.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            val userModel = UserModel(username, email, password, "", -1, "", -1, -1)
            _navigateToGenderLiveData.postValue(true)
        } else {
            _navigateToGenderLiveData.postValue(false)
            _registerErrorMessage.postValue("Fill all fields!!")

        }
    }

}