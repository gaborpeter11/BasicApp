package com.basesportperformance.data

import com.basesportperformance.data.local.SportsRecordsDao
import com.basesportperformance.data.local.toDomain
import com.basesportperformance.data.local.toEntity
import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.domain.model.SportsRecordSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SportsRecordsRepository @Inject constructor(
    private val sportsRecordsDao: SportsRecordsDao
) {
    fun observeSportsRecords(): Flow<List<SportsRecordDto>> {
        return sportsRecordsDao
            .observeSportsRecords()
            .map { it.toDomain() }
    }

    suspend fun saveSportsRecord(record: SportsRecordDto) {
        sportsRecordsDao.insertSportsRecord(record.toEntity())
    }

    internal suspend fun seedSampleRecordsIfNeeded() {
        if (sportsRecordsDao.getCount() > 0) return

        sportsRecordsDao.insertSportsRecords(SAMPLE_RECORDS.map { it.toEntity() })
    }

    private companion object {
        val SAMPLE_RECORDS = listOf(
            SportsRecordDto(
                id = 1,
                location = "City Park Track",
                time = "00:42:18",
                type = "Running",
                source = SportsRecordSource.Local
            ),
            SportsRecordDto(
                id = 2,
                location = "Aquatic Center",
                time = "00:18:44",
                type = "Swimming",
                source = SportsRecordSource.Remote
            ),
            SportsRecordDto(
                id = 3,
                location = "North Ridge",
                time = "01:12:03",
                type = "Cycling",
                source = SportsRecordSource.Local
            ),
            SportsRecordDto(
                id = 4,
                location = "River Row Club",
                time = "00:27:51",
                type = "Rowing",
                source = SportsRecordSource.Remote
            )
        )
    }
}

//TODO: use in larger projects
//suspend fun getSportsRecords(): List<SportsRecordDo> {
//    return when (val result = sportsRecordsProvider.fetchSportsRecords()) {
//        is CallResult.Success -> result.data?.map { mapper.map(it) } ?: emptyList()
//        is CallResult.Failure -> emptyList()
//    }
//}


