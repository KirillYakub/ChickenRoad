package com.chicken.chickenroad.data.repository

import com.chicken.chickenroad.data.data_source.LeadersEntity
import com.chicken.chickenroad.data.data_source.dao.InsertLeaderDao
import com.chicken.chickenroad.domain.repository.leaders.InsertLeaderRepos

class InsertLeadersReposImpl(
    private val insertLeadersDao: InsertLeaderDao
): InsertLeaderRepos {

    override suspend fun insertLeader(leaderData: Pair<String, Long>) {
        insertLeadersDao.insertLeader(
            LeadersEntity(
                usersName = leaderData.first,
                completeTime = leaderData.second
            )
        )
    }
}