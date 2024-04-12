package com.example.fitnessapp.feature.auth.register.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var username: String?,
    var email: String?,
    var password: String?,
    var gender: String?,
    var birthYear: Int?,
    var mobility: String?,
    var height: Int?,
    var weight: Int?
) : Parcelable {
    constructor() : this("", "", "", "", -1, "", -1, -1)

    override fun toString(): String {
        return "UserModel(username=$username, email=$email, password=$password, gender=$gender, birthYear=$birthYear, mobility=$mobility, height=$height, weight=$weight)"
    }
}
