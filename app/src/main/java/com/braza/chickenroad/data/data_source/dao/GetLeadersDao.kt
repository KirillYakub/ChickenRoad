package com.braza.chickenroad.data.data_source.dao

import androidx.room.Dao
import androidx.room.Query
import com.braza.chickenroad.data.data_source.LeadersEntity

@Dao
interface GetLeadersDao {

    @Query("SELECT * FROM leaderboard_table")
    fun getAllLeaders(): List<LeadersEntity>
}