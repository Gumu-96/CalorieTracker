package com.gumu.core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gumu.core.domain.model.ActivityLevel
import com.gumu.core.domain.model.Gender
import com.gumu.core.domain.model.GoalType
import com.gumu.core.domain.model.UserInfo
import com.gumu.core.domain.preferences.DataPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences(
    private val dataStore: DataStore<Preferences>
) : DataPreferences {
    private suspend fun <T : Any> saveData(key: Preferences.Key<T>, data: T) {
        dataStore.edit { it[key] = data }
    }

    override suspend fun saveGender(gender: Gender) = saveData(GENDER_KEY, gender.name)

    override suspend fun saveAge(age: Int) = saveData(AGE_KEY, age)

    override suspend fun saveWeight(weight: Float) = saveData(WEIGHT_KEY, weight)

    override suspend fun saveHeight(height: Int) = saveData(HEIGHT_KEY, height)

    override suspend fun saveActivityLevel(level: ActivityLevel) = saveData(ACTIVITY_LEVEL_KEY, level.name)

    override suspend fun saveGoalType(type: GoalType) = saveData(GOAL_TYPE_KEY, type.name)

    override suspend fun saveCarbRatio(ratio: Float) = saveData(CARB_RATIO_KEY, ratio)

    override suspend fun saveProteinRatio(ratio: Float) = saveData(PROTEIN_RATIO_KEY, ratio)

    override suspend fun saveFatRatio(ratio: Float) = saveData(FAT_RATIO_KEY, ratio)

    override fun loadUserInfo(): Flow<UserInfo> {
        return dataStore.data.map { prefs ->
            val gender = prefs[GENDER_KEY]?.let { Gender.fromString(it) } ?: Gender.Male
            val activityLevel = prefs[ACTIVITY_LEVEL_KEY]?.let { ActivityLevel.fromString(it) } ?: ActivityLevel.Medium
            val goalType = prefs[GOAL_TYPE_KEY]?.let { GoalType.fromString(it) } ?: GoalType.KeepWeight

            UserInfo(
                gender = gender,
                age = prefs[AGE_KEY] ?: -1,
                weight = prefs[WEIGHT_KEY] ?: -1f,
                height = prefs[HEIGHT_KEY] ?: -1,
                activityLevel = activityLevel,
                goalType = goalType,
                carbRatio = prefs[CARB_RATIO_KEY] ?: -1f,
                proteinRatio = prefs[PROTEIN_RATIO_KEY] ?: -1f,
                fatRatio = prefs[FAT_RATIO_KEY] ?: -1f
            )
        }
    }

    companion object {
        private val GENDER_KEY = stringPreferencesKey("gender_key")
        private val AGE_KEY = intPreferencesKey("age_key")
        private val WEIGHT_KEY = floatPreferencesKey("weight_key")
        private val HEIGHT_KEY = intPreferencesKey("height_key")
        private val ACTIVITY_LEVEL_KEY = stringPreferencesKey("activity_level_key")
        private val GOAL_TYPE_KEY = stringPreferencesKey("goal_type_key")
        private val CARB_RATIO_KEY = floatPreferencesKey("carb_ratio_key")
        private val PROTEIN_RATIO_KEY = floatPreferencesKey("protein_ratio_key")
        private val FAT_RATIO_KEY = floatPreferencesKey("fat_ratio_key")
    }
}
