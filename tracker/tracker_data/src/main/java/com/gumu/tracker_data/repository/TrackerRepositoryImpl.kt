package com.gumu.tracker_data.repository

import com.gumu.tracker_data.local.TrackerDao
import com.gumu.tracker_data.mapper.toTrackableFood
import com.gumu.tracker_data.mapper.toTrackedFood
import com.gumu.tracker_data.mapper.toTrackedFoodEntity
import com.gumu.tracker_data.remote.OpenFoodApi
import com.gumu.tracker_domain.model.TrackableFood
import com.gumu.tracker_domain.model.TrackedFood
import com.gumu.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(searchDto.products.mapNotNull { it.toTrackableFood() })
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.upsertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(date: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            date.dayOfMonth,
            date.monthValue,
            date.year
        ).map { foods ->
            foods.map { it.toTrackedFood() }
        }
    }
}
