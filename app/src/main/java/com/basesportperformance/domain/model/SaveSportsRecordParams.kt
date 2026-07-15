package com.basesportperformance.domain.model

data class SaveSportsRecordParams(
    val sport: String,
    val duration: String,
    val location: String,
    val storeLocally: Boolean
)