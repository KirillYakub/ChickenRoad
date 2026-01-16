package com.chicken.chickenroad.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chicken.chickenroad.util.Constants.LEADERBOARD_TABLE_NAME

@Entity(tableName = LEADERBOARD_TABLE_NAME)
data class LeadersEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usersName: String = "",
    val completeTime: Long = 0L
)

