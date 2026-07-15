package com.basesportperformance.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [SportsRecordEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(SportsRecordSourceConverter::class)
abstract class BaseSportPerformanceDatabase : RoomDatabase() {
    abstract fun sportsRecordsDao(): SportsRecordsDao
}

