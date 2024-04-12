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
}