package com.basesportperformance.domain.model

data class SportsRecordDto(
    val id: String,
    val location: String,
    val time: String,
    val type: String,
    val source: SportsRecordSource
)


