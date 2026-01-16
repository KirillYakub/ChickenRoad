package com.chicken.chickenroad.data.repository

import com.chicken.chickenroad.data.data_source.dao.GetLeadersDao
import com.chicken.chickenroad.domain.repository.leaders.GetLeadersRepos
import com.chicken.chickenroad.util.formatTimeFromSeconds

class GetLeadersReposImpl(
    private val getLeadersDao: GetLeadersDao
): GetLeadersRepos {

    override fun getAllLeaders(): List<Pair<String, String>> {
        return getLeadersDao.getAllLeaders().map { leadersEntity ->
            Pair(
                first = leadersEntity.usersName,
                second = formatTimeFromSeconds(leadersEntity.completeTime)
            )
        }
    }
}