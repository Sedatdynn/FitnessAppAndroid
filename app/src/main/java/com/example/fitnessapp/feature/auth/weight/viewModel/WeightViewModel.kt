package com.example.fitnessapp.feature.auth.weight.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.FirebaseManager
import com.example.fitnessapp.util.service.calculate.CalculateService
import com.example.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeightViewModel : ViewModel() {
    val TAG = "WeightViewModel"
    private var _isCompleted = MutableLiveData<Boolean>(false)
    val isCompleted: LiveData<Boolean> get() = _isCompleted

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun calculateTotalPoints(user: UserModel) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                val totalPoints = withContext(Dispatchers.IO) {
                    CalculateService.calculateTotalPoints(user)
                }
                val updatedUser = user.copy(userRightPoint = totalPoints)
                Log.i(TAG, "TOTAL POINT: $totalPoints")
                saveUser(updatedUser)
            } catch (e: Exception) {
                Log.e(TAG, "Hata oluştu: ${e.message}", e)
                _isLoading.postValue(false)
                _errorMessage.postValue(e.message)
                _isCompleted.postValue(false)
            }
        }
    }

    private fun saveUser(updatedUser: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val authResult = FirebaseManager.saveUser(updatedUser)
                if (authResult.isSuccess) {
                    _isLoading.postValue(false)
                    _isCompleted.postValue(true)
                } else {
                    _isCompleted.postValue(false)
                    _isLoading.postValue(false)
                    _errorMessage.postValue(authResult.exceptionOrNull()?.message)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Hata oluştu: ${e.message}", e)
                _isCompleted.postValue(false)
                _isLoading.postValue(false)
                _errorMessage.postValue(e.message)
            }
        }
    }

}