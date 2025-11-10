package com.example.basicapp.data

import kotlinx.serialization.Serializable

@Serializable
data class VersionResponse(
    val android: Long
)
