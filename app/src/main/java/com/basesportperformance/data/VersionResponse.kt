package com.basesportperformance.data

import kotlinx.serialization.Serializable

@Serializable
data class VersionResponse(
    val android: Long
)
