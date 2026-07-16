package com.basesportperformance.data.remote

import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.domain.model.SportsRecordSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SportsRecordsRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authManager: FirebaseAuthManager
) {
    fun observeSportsRecords(): Flow<List<SportsRecordDto>> = callbackFlow {
        val ownerId = authManager.currentUserId()

        val registration = firestore.collection(COLLECTION)
            .whereEqualTo(FIELD_OWNER_ID, ownerId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val records = snapshot?.documents.orEmpty().mapNotNull { document ->
                    document.toObject(SportsRecordRemoteDto::class.java)?.toDto(document.id)
                }
                trySend(records)
            }

        awaitClose { registration.remove() }
    }

    fun observeSportsRecord(id: String): Flow<SportsRecordDto?> = callbackFlow {
        val ownerId = authManager.currentUserId()

        val registration = firestore.collection(COLLECTION)
            .document(id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                // Documents owned by someone else must never reach the caller, even if the id is known.
                val record = snapshot
                    ?.takeIf { it.exists() }
                    ?.let { document ->
                        document.toObject(SportsRecordRemoteDto::class.java)
                            ?.takeIf { it.ownerId == ownerId }
                            ?.toDto(document.id)
                    }
                trySend(record)
            }

        awaitClose { registration.remove() }
    }

    suspend fun saveSportsRecord(record: SportsRecordDto) {
        val ownerId = authManager.currentUserId()
        val remoteDto = SportsRecordRemoteDto(
            ownerId = ownerId,
            type = record.type,
            time = record.time,
            location = record.location
        )
        firestore.collection(COLLECTION).add(remoteDto).await()
    }

    private fun SportsRecordRemoteDto.toDto(id: String) = SportsRecordDto(
        id = id,
        location = location,
        time = time,
        type = type,
        source = SportsRecordSource.Remote
    )

    private companion object {
        const val COLLECTION = "sportsRecords"
        const val FIELD_OWNER_ID = "ownerId"
    }
}
