package com.braza.chickenroad.data.di

import android.content.Context
import com.braza.chickenroad.data.repository.PlayerSoundsReposImpl
import com.braza.chickenroad.domain.repository.PlayerSoundsRepos
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
