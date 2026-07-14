package com.basesportperformance.ui.sportsrecords.model

import androidx.compose.runtime.Immutable

@Immutable
sealed interface SportsRecordsUiState {
    val selectedTab: SportsRecordsTab

    @Immutable
    data class Loading(
        override val selectedTab: SportsRecordsTab = SportsRecordsTab.All
    ) : SportsRecordsUiState

    @Immutable
    data class Empty(
        override val selectedTab: SportsRecordsTab = SportsRecordsTab.All
    ) : SportsRecordsUiState

    @Immutable
    data class Error(
        val message: String,
        override val selectedTab: SportsRecordsTab = SportsRecordsTab.All
    ) : SportsRecordsUiState

    @Immutable
    data class Success(
        val records: List<SportsRecord>,
        override val selectedTab: SportsRecordsTab = SportsRecordsTab.All
    ) : SportsRecordsUiState
}

