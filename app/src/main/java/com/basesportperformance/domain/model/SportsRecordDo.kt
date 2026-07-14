package com.basesportperformance.domain.model

data class SportsRecordDo(
    val id: Long,
    val name: String,
    val time: String,
    val type: String,
    val source: SportsRecordSource
)


