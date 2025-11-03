package com.braza.chickenroad.domain.repository

interface RatingRepos {
    suspend fun saveRatingTime(time: Int)
    suspend fun readRatingTime(): Int?
}