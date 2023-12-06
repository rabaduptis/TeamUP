package com.root14.teamup.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.root14.teamup.model.entity.TeamModel

@Dao
interface TeamDao {
    @Query("SELECT * FROM TeamModel")
    suspend fun getAll(): List<TeamModel>

    @Query("SELECT * FROM TeamModel WHERE userName LIKE :userName")
    suspend fun findByUserName(userName: String): TeamModel

    @Query("SELECT * FROM TeamModel WHERE teamName LIKE :teamName")
    suspend fun findByTeamName(teamName: String): TeamModel

    @Insert
    suspend fun insertAll(vararg users: TeamModel)

    @Query("DELETE FROM TeamModel")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(user: TeamModel)

    @Update
    suspend fun update(user: TeamModel)

    @Delete
    suspend fun delete(user: TeamModel)

    @Query("SELECT count(uid) FROM TeamModel") // items is the table in the @Entity tag of ItemsYouAreStoringInDB.kt, id is a primary key which ensures each entry in DB is unique
    suspend fun getTeamCount(): Int
}