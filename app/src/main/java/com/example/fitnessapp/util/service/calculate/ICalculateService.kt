package com.example.fitnessapp.util.service.calculate

import com.example.model.UserModel

interface ICalculateService {
    fun calculateBmi(height: Int, weight: Int): String
    suspend fun calculateTotalPoints(params: UserModel): Int
    fun calculateWeight(weight: Int)
    fun calculateAge(age: Int)
    fun calculateMobility(mobility: String)
}