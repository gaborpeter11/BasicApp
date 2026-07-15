package com.basesportperformance.ui.sportsrecords.model

sealed interface SportsRecordsAction {
    data object Retry : SportsRecordsAction
    data object AddRecord : SportsRecordsAction
    data class TabSelected(val tab: SportsRecordsTab) : SportsRecordsAction
    data class RecordClicked(val recordId: Long) : SportsRecordsAction
}