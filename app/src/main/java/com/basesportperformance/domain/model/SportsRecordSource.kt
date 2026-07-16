package com.basesportperformance.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class SportsRecordSource {
    Local,
    Remote
}