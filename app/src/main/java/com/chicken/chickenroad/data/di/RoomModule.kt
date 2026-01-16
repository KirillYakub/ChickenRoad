package com.chicken.chickenroad.data.di

import android.content.Context
import androidx.room.Room
import com.chicken.chickenroad.data.data_source.LeadersDatabase
import com.chicken.chickenroad.data.repository.GetLeadersReposImpl
import com.chicken.chickenroad.data.repository.InsertLeadersReposImpl
import com.chicken.chickenroad.domain.repository.leaders.GetLeadersRepos
import com.chicken.chickenroad.domain.repository.leaders.InsertLeaderRepos
import com.chicken.chickenroad.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideReceiptsDatabase(
        @ApplicationContext context: Context
    ): LeadersDatabase {
        return Room.databaseBuilder(
            context,
            LeadersDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideGetLeadersRepos(
        leadersDatabase: LeadersDatabase
    ): GetLeadersRepos {
        return GetLeadersReposImpl(
            getLeadersDao = leadersDatabase.getLeadersDao()
        )
    }

    @Provides
    @Singleton
    fun provideInsertLeadersRepos(
        leadersDatabase: LeadersDatabase
    ): InsertLeaderRepos {
        return InsertLeadersReposImpl(
            insertLeadersDao = leadersDatabase.insertLeadersDao()
        )
    }
}