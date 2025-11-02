package com.braza.chickenroad.domain.repository

interface FirstInputRepos {
    suspend fun saveFirstInput(isFirstInput: Boolean)
    suspend fun readFirstInput(): Boolean
}