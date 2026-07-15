package com.basesportperformance.ui.sportsrecords.model

import androidx.compose.runtime.Immutable
import com.basesportperformance.domain.model.SportsRecordSource

@Immutable
data class SportsRecord(
    val id: Long,
    val time: String,
    val type: String,
    val source: SportsRecordSource
)