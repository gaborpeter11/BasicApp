package com.basesportperformance.ui.sportsrecords

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.basesportperformance.ui.sportsrecords.model.SportsRecordsAction

@Composable
fun SportsRecordsRoute(
    onNavigateToAddRecord: () -> Unit,
    onNavigateToRecordDetail: (Long) -> Unit,
    sportsRecordsViewModel: SportsRecordsViewModel = hiltViewModel()
) {
    val uiState by sportsRecordsViewModel.uiState.collectAsState()

    SportsRecordsScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                SportsRecordsAction.AddRecord -> onNavigateToAddRecord()
                is SportsRecordsAction.RecordClicked -> onNavigateToRecordDetail(action.recordId)
                else -> sportsRecordsViewModel.onAction(action)
            }
        }
    )
}
