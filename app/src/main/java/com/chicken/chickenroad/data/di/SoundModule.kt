package com.chicken.chickenroad.data.di

import android.content.Context
import com.chicken.chickenroad.data.repository.PlayerSoundsReposImpl
import com.chicken.chickenroad.domain.repository.PlayerSoundsRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SoundModule {

    @Provides
    @Singleton
    fun providePlayerSounds(@ApplicationContext context: Context): PlayerSoundsRepos {
        return PlayerSoundsReposImpl(context)
    }
}
