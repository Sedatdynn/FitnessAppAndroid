package com.example.fitnessapp.feature.auth.login.model

data class LoginModel(var email: String?, var password: String?){
    constructor() : this("", "")
}
