package com.basesportperformance.navigation

import com.basesportperformance.domain.model.SportsRecordSource
import kotlinx.serialization.Serializable

@Serializable
internal data object SportsRecords

@Serializable
internal data object AddRecord

@Serializable
internal data class RecordDetail(val id: String, val source: SportsRecordSource)
