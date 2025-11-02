package com.braza.chickenroad.domain.repository

interface MaxOpenedLevelRepos {
    suspend fun saveMaxOpenedLevel(level: Int)
    suspend fun readMaxOpenedLevel(): Int
}