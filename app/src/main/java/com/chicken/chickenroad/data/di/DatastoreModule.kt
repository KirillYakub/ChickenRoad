package com.chicken.chickenroad.data.di

import android.content.Context
import com.chicken.chickenroad.data.DatastorePreferences
import com.chicken.chickenroad.domain.repository.FirstInputRepos
import com.chicken.chickenroad.domain.repository.MaxOpenedLevelRepos
import com.chicken.chickenroad.domain.repository.RatingRepos
import com.chicken.chickenroad.domain.repository.SettingsRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun provideDatastorePreferences(
        @ApplicationContext context: Context
    ): DatastorePreferences {
        return DatastorePreferences(context)
    }

    @Provides
    @Singleton
    fun provideSettingsRepos(
        datastorePreferences: DatastorePreferences
    ): SettingsRepos =
        datastorePreferences

    @Provides
    @Singleton
    fun provideFirstInputRepos(
        datastorePreferences: DatastorePreferences
    ): FirstInputRepos =
        datastorePreferences

    @Provides
    @Singleton
    fun provideMaxOpenedLevelRepos(
        datastorePreferences: DatastorePreferences
    ): MaxOpenedLevelRepos =
        datastorePreferences

    @Provides
    @Singleton
    fun provideRatingRepos(
        datastorePreferences: DatastorePreferences
    ): RatingRepos =
        datastorePreferences
}