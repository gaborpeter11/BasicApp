package com.basesportperformance.data.remote

/**
 * Firestore document shape for the "sportsRecords" collection.
 * Requires a no-arg constructor (default values) for Firestore's automatic deserialization.
 */
data class SportsRecordRemoteDto(
    val ownerId: String = "",
    val type: String = "",
    val time: String = "",
    val location: String = ""
)
