package com.gumu.core.domain.preferences

import com.gumu.core.domain.model.ActivityLevel
import com.gumu.core.domain.model.Gender
import com.gumu.core.domain.model.GoalType
import com.gumu.core.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface DataPreferences {
    suspend fun saveGender(gender: Gender)
    suspend fun saveAge(age: Int)
    suspend fun saveWeight(weight: Float)
    suspend fun saveHeight(height: Int)
    suspend fun saveActivityLevel(level: ActivityLevel)
    suspend fun saveGoalType(type: GoalType)
    suspend fun saveCarbRatio(ratio: Float)
    suspend fun saveProteinRatio(ratio: Float)
    suspend fun saveFatRatio(ratio: Float)

    fun loadUserInfo(): Flow<UserInfo>

    suspend fun saveShouldShowOnboarding(shouldShow: Boolean)
    fun getShouldShowOnboarding(): Flow<Boolean>
}
