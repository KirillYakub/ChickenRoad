package com.braza.chickenroad.data.repository

import com.braza.chickenroad.data.data_source.dao.GetLeadersDao
import com.braza.chickenroad.domain.repository.leaders.GetLeadersRepos
import com.braza.chickenroad.util.formatTimeFromSeconds

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