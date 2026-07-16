package com.basesportperformance.di

import android.content.Context
import androidx.room.Room
import com.basesportperformance.data.local.BaseSportPerformanceDatabase
import com.basesportperformance.data.local.SportsRecordsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BaseSportPerformanceDatabase = Room.databaseBuilder(
        context,
        BaseSportPerformanceDatabase::class.java,
        "base_sport_performance.db"
    ).build()

    @Provides
    @Singleton
    fun provideSportsRecordsDao(
        database: BaseSportPerformanceDatabase
    ): SportsRecordsDao = database.sportsRecordsDao()
}
