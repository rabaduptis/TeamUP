package com.root14.teamup.data.repo

import com.root14.teamup.data.dao.TeamDao
import com.root14.teamup.model.entity.TeamModel
import javax.inject.Inject

class TeamRepo @Inject constructor(private val dao: TeamDao) {
    suspend fun getAllTeams() = dao.getAll()
    suspend fun getTeamCount() = dao.getTeamCount()
    suspend fun getTeam(userName: String) = dao.findByTeamName(userName)
    suspend fun insertTeam(team: TeamModel) = dao.insert(team)
    suspend fun updateTeam(team: TeamModel) = dao.update(team)

    suspend fun deleteTeam(team: TeamModel) = dao.delete(team)
}