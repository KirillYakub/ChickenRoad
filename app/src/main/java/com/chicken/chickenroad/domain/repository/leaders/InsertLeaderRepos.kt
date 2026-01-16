package com.chicken.chickenroad.domain.repository.leaders

interface InsertLeaderRepos {
    suspend fun insertLeader(leaderData: Pair<String, Long>)
}