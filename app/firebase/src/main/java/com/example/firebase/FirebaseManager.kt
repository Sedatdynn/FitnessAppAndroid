package com.example.firebase

import android.annotation.SuppressLint
import android.util.Log
import com.example.cache.CacheKeys
import com.example.cache.CacheManager
import com.example.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
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
            if (result.user != null && result.user!!.isEmailVerified) {
                result.user!!.getIdToken(true).addOnSuccessListener {
                    if (it.token != null) {
                        CacheManager.saveString(CacheKeys.TOKEN, it.token!!)
                    }
                }
                Log.e(TAG, "completed verification of your email!")
                AuthResult.Success(result.user!!)
            } else {
                Log.e(TAG, "Please complete verification of your email!")
                AuthResult.Error("Please complete verification of your email!")
            }
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

    // Google Authentication
    fun googleSignIn(account: GoogleSignInAccount): Result<Boolean> {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
            Log.i(TAG, "Google Sign in authentication SUCCESSFULL!!!${currentUser?.email}}")
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Google Sign in authentication failed: ${e.message.toString()}")
            Result.failure(e)
        }
    }

    //signOut
    suspend fun signOut(): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                auth.signOut()
                Log.i(TAG, "signOut completed successfully!")
                Result.success(true)
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An error occurred during sign out."
                Log.e(TAG, "signOut error: $errorMessage")
                Result.failure(e)
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
            sendEmailVerification(currentUser!!)
            val usersRef = db.collection("users")
            usersRef.document(currentUserUid).set(user).await()
            Log.d(TAG, "User successfully added to Firestore")
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error adding user to Firestore: ${e.message}", e)
            Result.failure(e)
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnSuccessListener {
            Log.i(TAG, "sendEmailVerification successful!")
        }.addOnFailureListener { exception ->
            Log.e(TAG, "sendEmailVerification error: ${exception.message}", exception)
        }
    }

    // reset password with sending reset password email!
    suspend fun resetPassword(email: String): Pair<Boolean, String?> {
        return try {
            val isEmailRegistered = isEmailRegistered(email.trim())
            if (isEmailRegistered) {
                auth.sendPasswordResetEmail(email.trim()).await()
                true to null
            } else {
                Log.e(TAG, "${email.trim()}: Email doesn't exist!!")
                false to "Email doesn't exist!!"
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            false to e.message
        }
    }

    //check if email is exist during reset the password!!
    private suspend fun isEmailRegistered(email: String): Boolean {
        val querySnapshot = db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .await()
        return !querySnapshot.isEmpty
    }
}

sealed class AuthResult {
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Error(val errorMessage: String) : AuthResult()
}
