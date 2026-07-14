package com.basesportperformance.data

import com.basesportperformance.domain.model.SportsRecordDo
import com.basesportperformance.domain.model.SportsRecordSource
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("unused")
@Singleton
class SportsRecordsRepository @Inject constructor() {

    @Suppress("unused")
    suspend fun getSportsRecords(): List<SportsRecordDo> {
        delay(9000)
        return sampleRecords
    }

    private companion object {
        val sampleRecords = listOf(
            SportsRecordDo(
                id = 1,
                name = "Morning Run",
                time = "00:42:18",
                type = "Running",
                source = SportsRecordSource.Local
            ),
            SportsRecordDo(
                id = 2,
                name = "Pool Sprint",
                time = "00:18:44",
                type = "Swimming",
                source = SportsRecordSource.Remote
            ),
            SportsRecordDo(
                id = 3,
                name = "Hill Climb",
                time = "01:12:03",
                type = "Cycling",
                source = SportsRecordSource.Local
            ),
            SportsRecordDo(
                id = 4,
                name = "Evening Recovery",
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


