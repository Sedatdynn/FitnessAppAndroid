package com.example.fitnessapp.util.service.calculate

import com.example.fitnessapp.util.enums.GenderEnum
import com.example.model.UserModel
import java.util.Calendar
import kotlin.math.pow

object CalculateService : ICalculateService {
    private var updatedTotalPoint = 0
    override fun calculateBmi(height: Int, weight: Int): String {
        val result = weight.toDouble() / (height.toDouble() / 100).pow(2)
        return String.format("%.2f", result)
    }
    override suspend fun calculateTotalPoints(params: UserModel): Int {
        calculateAge(params.birthYear!!)
        calculateWeight(params.weight!!)

        if (params.height!! >= 160) updatedTotalPoint += 2 else updatedTotalPoint += 1

        if (params.gender == GenderEnum.Female.name) updatedTotalPoint += 7 else updatedTotalPoint += 15

        calculateMobility(params.mobility!!)
        kotlinx.coroutines.delay(2000)

        return updatedTotalPoint
    }

    override fun calculateWeight(weight: Int) {
        val pointsMap = mapOf(
            40 to 4,
            50 to 5,
            60 to 6,
            70 to 7,
            80 to 8,
            90 to 9,
            100 to 10,
            110 to 11,
            120 to 12,
            130 to 13,
            140 to 14,
            150 to 15,
            160 to 16
        )

        pointsMap.forEach { (rangeStart, points) ->
            if (weight in rangeStart..(rangeStart + 9)) {
                updatedTotalPoint += points
            }
        }
    }

    override fun calculateAge(age: Int) {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val calculatedAge = currentYear - age
        when {
            calculatedAge <= 20 -> updatedTotalPoint += 5
            calculatedAge in 21..35 -> updatedTotalPoint += 4
            calculatedAge in 36..50 -> updatedTotalPoint += 3
            calculatedAge in 51..65 -> updatedTotalPoint += 2
        }
    }

    override fun calculateMobility(mobility: String) {
        val mobilityControl = mobility.substring(0, 1)
        when (mobilityControl) {
            "D" -> updatedTotalPoint += 0
            "B" -> updatedTotalPoint += 2
            "T" -> updatedTotalPoint += 4
            "A" -> updatedTotalPoint += 6
        }
    }
}
