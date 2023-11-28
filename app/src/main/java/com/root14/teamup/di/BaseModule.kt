package com.root14.teamup.di

import android.content.Context
import com.root14.teamup.data.PrefDataStoreManager
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

}