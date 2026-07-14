package com.basesportperformance.ui.addrecord.model

import androidx.compose.runtime.Immutable

@Immutable
data class AddRecordUiState(
    val selectedSport: SportType = SportType.RUNNING,
    val location: String = "City Park",
    val duration: String = "00:42:18",
    val storeLocally: Boolean = true,
    val isAddRecordEnabled: Boolean = true
)
