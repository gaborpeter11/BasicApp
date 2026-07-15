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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

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

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
}
