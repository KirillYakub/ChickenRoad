package com.braza.chickenroad.domain.repository.leaders

interface GetLeadersRepos {
    fun getAllLeaders(): List<Pair<String, String>>
}