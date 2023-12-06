package com.root14.teamup.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.root14.teamup.model.entity.TeamModel

class TeamTypeConverter {
    @TypeConverter
    fun fromTeamModel(teamModel: TeamModel): String = Gson().toJson(teamModel)

    @TypeConverter
    fun toTeamModel(string: String): TeamModel = Gson().fromJson(string, TeamModel::class.java)
}