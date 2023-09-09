package com.gumu.tracker_domain.di

import com.gumu.core.domain.preferences.DataPreferences
import com.gumu.tracker_domain.repository.TrackerRepository
import com.gumu.tracker_domain.usecase.CalculateMealNutrients
import com.gumu.tracker_domain.usecase.DeleteTrackedFood
import com.gumu.tracker_domain.usecase.GetFoodsForDate
import com.gumu.tracker_domain.usecase.SearchFood
import com.gumu.tracker_domain.usecase.TrackFood
import com.gumu.tracker_domain.usecase.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {
    @Provides
    @ViewModelScoped
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        dataPreferences: DataPreferences
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(dataPreferences)
        )
    }
}
