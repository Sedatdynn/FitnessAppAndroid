package com.example.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object FirebaseManager { // Singleton
    const val TAG = "FirebaseManager"
    var auth = FirebaseAuth.getInstance()
    val currentUser: FirebaseUser?
        get() = auth.currentUser


    //signIn
    suspend fun signIn(email: String, password: String): AuthResult  {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            AuthResult .Success(result.user!!)
        } catch (e: Exception) {
            Log.e(TAG, "signIn error: ${e.message}")
            AuthResult .Error(e.message ?: "An error occurred during sign in.")
        }
    }


    //signUp
    suspend fun signUp(email: String, password: String): AuthResult  {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                AuthResult .Success(result.user!!)
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An error occurred during sign up."
                Log.e(TAG, "signUp error: $errorMessage")
                AuthResult .Error(errorMessage)
            }
        }
    }

    //signOut
    suspend fun signOut(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                auth.signOut()
                Result.success(true) // Başarılı çıkış işlemi
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An error occurred during sign out."
                Log.e(TAG, "signOut error: $errorMessage")
                Result.failure(e) // Çıkış işlemi sırasında hata oluştu
            }
        }
    }
}

sealed class AuthResult  {
    data class Success(val user: FirebaseUser) : AuthResult ()
    data class Error(val errorMessage: String) : AuthResult ()
}
