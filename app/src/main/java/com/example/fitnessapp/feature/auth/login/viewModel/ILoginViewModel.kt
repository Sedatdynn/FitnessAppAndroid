package com.example.fitnessapp.feature.auth.login.viewModel

interface ILoginViewModel {
    fun signIn()
    fun setEmail(email: String)
    fun setPassword(password: String)
}