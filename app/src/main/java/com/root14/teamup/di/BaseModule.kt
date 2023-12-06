package com.root14.teamup.di

import android.content.Context
import androidx.room.Room
import com.root14.teamup.data.Database
import com.root14.teamup.data.PrefDataStoreManager
import com.root14.teamup.data.dao.TeamDao
import com.root14.teamup.data.repo.TeamRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object BaseModule {
    @Singleton
    @Provides
    fun providePrefDataStoreManager(@ApplicationContext context: Context): PrefDataStoreManager {
        return PrefDataStoreManager.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app, Database::class.java, "teamUp_db"
    ).build()

    @Singleton
    @Provides
    fun provideTeamDao(db: Database) = db.teamDao()

    @Singleton
    @Provides
    fun provideTeamRepo(teamDao: TeamDao): TeamRepo {
        return TeamRepo(teamDao)
    }
}