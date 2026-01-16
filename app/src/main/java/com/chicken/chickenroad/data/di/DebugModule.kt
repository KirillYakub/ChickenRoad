package com.chicken.chickenroad.data.di

import android.content.Context
import com.chicken.chickenroad.util.CheckDebugEnable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DebugModule {

    @Provides
    @Singleton
    fun provideCheckDebugEnable(
        @ApplicationContext context: Context
    ): CheckDebugEnable {
        return CheckDebugEnable(context)
    }
}