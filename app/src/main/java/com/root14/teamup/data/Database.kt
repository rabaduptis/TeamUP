package com.root14.teamup.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.root14.teamup.data.dao.TeamDao
import com.root14.teamup.model.entity.TeamModel


@Database(
    entities = [TeamModel::class], // Tell the database the entries will hold data of this type
    version = 1
)
@TypeConverters(TeamTypeConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun teamDao(): TeamDao
}