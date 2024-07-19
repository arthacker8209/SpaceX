package com.deepak.spacex.data.di

import android.content.Context
import com.deepak.spacex.data.db.LaunchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context) = LaunchDatabase.getInstance(context)

    @Provides
    fun providesLaunchDao(appDatabase: LaunchDatabase) = appDatabase.launchDao()

    @Provides
    fun providesBookmarkedItemDao(appDatabase: LaunchDatabase ) = appDatabase.bookmarkItemDao()
}
