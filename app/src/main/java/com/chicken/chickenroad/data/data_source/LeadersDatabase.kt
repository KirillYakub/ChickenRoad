package com.chicken.chickenroad.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chicken.chickenroad.data.data_source.dao.GetLeadersDao
import com.chicken.chickenroad.data.data_source.dao.InsertLeaderDao

@Database(entities = [LeadersEntity::class], version = 1)
abstract class LeadersDatabase : RoomDatabase() {
    abstract fun getLeadersDao(): GetLeadersDao
    abstract fun insertLeadersDao(): InsertLeaderDao
}