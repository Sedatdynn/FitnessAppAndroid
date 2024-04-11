package com.example.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser

object FirebaseManager { // Singleton

    const val TAG = "FirebaseManager"
    var auth = FirebaseAuth.getInstance()
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    //signIn
    fun signIn(
        email: String, password: String,
        onSuccess: (uid: String?) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { result ->
            val uid = result.user?.uid
            onSuccess(uid)
        }.addOnFailureListener { error ->
            if (error is FirebaseAuthException) {
                Log.e(
                    TAG,
                    "firebaseAuthException code: ${error.errorCode} message: ${error.message}"
                )
            } else {
                Log.e(TAG, "firebaseException message: ${error.message}")
            }
            onError(error.message ?: "Error")
        }
    }

    //signUp
    fun signUp(
        email: String, password: String,
        onSuccess: (uid: String?) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { result ->
            val uid = result.user?.uid
            onSuccess(uid)
        }.addOnFailureListener { error ->
            if (error is FirebaseAuthException) {
                Log.e(
                    TAG,
                    "firebaseAuthException code: ${error.errorCode} message: ${error.message}"
                )
            } else {
                Log.e(TAG, "firebaseException message: ${error.message}")
            }
            onError(error.message ?: "Error")
        }
    }

    //sign out
    fun signOut(onSignOutError: (errorMessage: String?) -> Unit) {
        try {
            auth.signOut()
            onSignOutError(null)
        } catch (e: Exception) {
            Log.e(TAG, "Error signing out: ${e.message}")
            e.message?.let { onSignOutError(it) }
        }
    }


}
