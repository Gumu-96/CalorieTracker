package com.gumu.onboarding_domain.usecase

import com.gumu.core.R
import com.gumu.core.util.UiText
import javax.inject.Inject

class ValidateNutrients @Inject constructor() {
    operator fun invoke(
        carbsRatioStr: String,
        proteinRatioStr: String,
        fatRatioStr: String
    ): Result {
        val carbsRatio = carbsRatioStr.toIntOrNull()
        val proteinRatio = proteinRatioStr.toIntOrNull()
        val fatRatio = fatRatioStr.toIntOrNull()

        if (carbsRatio == null || proteinRatio == null || fatRatio == null) {
            return Result.Error(UiText.StringResource(R.string.error_invalid_values))
        }

        if (carbsRatio + proteinRatio + fatRatio != 100) {
            return Result.Error(UiText.StringResource(R.string.error_not_100_percent))
        }

        return Result.Success(
            carbsRatio / 100f,
            proteinRatio / 100f,
            fatRatio / 100f
        )
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float
        ) : Result()
        data class Error(val message: UiText) : Result()
    }
}
