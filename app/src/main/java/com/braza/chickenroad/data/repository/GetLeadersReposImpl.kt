package com.braza.chickenroad.data.repository

import android.annotation.SuppressLint
import com.braza.chickenroad.data.data_source.dao.GetLeadersDao
import com.braza.chickenroad.domain.repository.leaders.GetLeadersRepos

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

    @SuppressLint("DefaultLocale")
    private fun formatTimeFromSeconds(seconds: Long): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}