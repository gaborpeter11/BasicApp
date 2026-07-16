package com.basesportperformance.data

import com.basesportperformance.data.local.SportsRecordsDao
import com.basesportperformance.data.local.toDomain
import com.basesportperformance.data.local.toDomainModel
import com.basesportperformance.data.local.toEntity
import com.basesportperformance.data.remote.SportsRecordsRemoteDataSource
import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.domain.model.SportsRecordSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SportsRecordsRepository @Inject constructor(
    private val sportsRecordsDao: SportsRecordsDao,
    private val remoteDataSource: SportsRecordsRemoteDataSource
) {
    fun observeSportsRecords(): Flow<List<SportsRecordDto>> {
        val localRecords = sportsRecordsDao.observeSportsRecords().map { it.toDomain() }
        val remoteRecords = remoteDataSource.observeSportsRecords()
            .resilientToRemoteFailures(fallback = emptyList())

        return combine(localRecords, remoteRecords) { local, remote ->
            local + remote
        }
    }

    fun observeSportsRecord(id: String, source: SportsRecordSource): Flow<SportsRecordDto?> {
        return when (source) {
            SportsRecordSource.Local -> {
                val localId = id.toLongOrNull()
                    ?: error("Invalid local record id: '$id'")
                sportsRecordsDao.observeSportsRecord(localId).map { it?.toDomainModel() }
            }

            SportsRecordSource.Remote -> remoteDataSource.observeSportsRecord(id)
                .resilientToRemoteFailures(fallback = null)
        }
    }

    suspend fun saveSportsRecord(record: SportsRecordDto) {
        when (record.source) {
            SportsRecordSource.Local -> sportsRecordsDao.insertSportsRecord(record.toEntity())
            SportsRecordSource.Remote -> remoteDataSource.saveSportsRecord(record)
        }
    }

    /**
     * Firestore listener errors are terminal for that listener - without a retry, a single
     * transient failure would end this flow for good and freeze whatever it feeds (e.g. combine()
     * never re-collecting it again). Retrying re-attaches a fresh listener instead of giving up,
     * and emits the fallback value immediately so callers (e.g. combine() with a local-only flow)
     * aren't blocked behind the network round-trip of the first Firestore/auth call.
     */
    private fun <T> Flow<T>.resilientToRemoteFailures(fallback: T): Flow<T> =
        retryWhen { _, _ ->
            delay(REMOTE_RETRY_DELAY_MILLIS)
            true
        }.onStart { emit(fallback) }

    private companion object {
        const val REMOTE_RETRY_DELAY_MILLIS = 5_000L

        // Kept for reference only
        val SAMPLE_RECORDS = listOf(
            SportsRecordDto(
                id = "1",
                location = "City Park Track",
                time = "00:42:18",
                type = "Running",
                source = SportsRecordSource.Local
            ),
            SportsRecordDto(
                id = "2",
                location = "Aquatic Center",
                time = "00:18:44",
                type = "Swimming",
                source = SportsRecordSource.Local
            ),
            SportsRecordDto(
                id = "3",
                location = "North Ridge",
                time = "01:12:03",
                type = "Cycling",
                source = SportsRecordSource.Local
            ),
            SportsRecordDto(
                id = "4",
                location = "River Row Club",
                time = "00:27:51",
                type = "Rowing",
                source = SportsRecordSource.Local
            )
        )
    }
}
