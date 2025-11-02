package com.braza.chickenroad.data.data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import com.braza.chickenroad.data.data_source.LeadersEntity

@Dao
interface InsertLeaderDao {

    @Insert
    suspend fun insertLeader(leader: LeadersEntity)
}