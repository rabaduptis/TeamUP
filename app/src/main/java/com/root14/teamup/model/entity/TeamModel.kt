package com.root14.teamup.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamModel(
    @PrimaryKey val uid: Int? = null,
    @ColumnInfo val userName: String,
    @ColumnInfo val teamName: String,
    @ColumnInfo val teamDescription: String,
    @ColumnInfo val teamUUID: String
)
