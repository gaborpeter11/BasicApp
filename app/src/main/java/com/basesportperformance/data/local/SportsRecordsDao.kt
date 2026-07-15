package com.basesportperformance.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SportsRecordsDao {

    @Query("SELECT * FROM sports_records ORDER BY id DESC")
    fun observeSportsRecords(): Flow<List<SportsRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSportsRecord(record: SportsRecordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSportsRecords(records: List<SportsRecordEntity>)

    @Query("SELECT COUNT(*) FROM sports_records")
    suspend fun getCount(): Int
}


