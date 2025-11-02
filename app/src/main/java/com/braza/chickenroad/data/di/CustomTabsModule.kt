package com.braza.chickenroad.data.di

import android.content.Context
import com.braza.chickenroad.util.CustomTabsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CustomTabsModule {

    @Provides
    @Singleton
    fun provideCustomTabsHelper(
        @ApplicationContext context: Context
    ): CustomTabsHelper {
        return CustomTabsHelper(context)
    }
}