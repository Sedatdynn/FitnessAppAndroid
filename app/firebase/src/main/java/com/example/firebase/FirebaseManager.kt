package com.example.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.example.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object FirebaseManager { // Singleton
    const val TAG = "FirebaseManager"
    var auth = FirebaseAuth.getInstance()

    @SuppressLint("StaticFieldLeak")
    private val db = Firebase.firestore
    val currentUser: FirebaseUser?
        get() = auth.currentUser


    //signIn
    suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user!!)
        } catch (e: Exception) {
            Log.e(TAG, "signIn error: ${e.message}")
            AuthResult.Error(e.message ?: "An error occurred during sign in.")
        }
    }


    //signUp
    private suspend fun signUp(email: String, password: String): AuthResult {
        return withContext(Dispatchers.IO) {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                AuthResult.Success(result.user!!)
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An error occurred during sign up."
                Log.e(TAG, "signUp error: $errorMessage")
                AuthResult.Error(errorMessage)
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

    //save data
    suspend fun saveUser(user: UserModel): Result<Boolean> {
        var errorMessage = ""
        when (val createUser = signUp(user.email!!, user.password!!)) {
            is AuthResult.Success -> {}
            is AuthResult.Error -> {
                errorMessage = createUser.errorMessage
            }
        }
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid.isNullOrEmpty()) {
            return Result.failure(Exception(errorMessage))
        }
        return try {
            val usersRef = db.collection("users")
            usersRef.document(currentUserUid).set(user).await()
            Log.d(TAG, "User successfully added to Firestore")
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error adding user to Firestore: ${e.message}", e)
            Result.failure(e)
        }
    }

}

sealed class AuthResult {
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Error(val errorMessage: String) : AuthResult()
}
