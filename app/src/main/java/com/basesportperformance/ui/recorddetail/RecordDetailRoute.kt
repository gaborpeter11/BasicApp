package com.basesportperformance.ui.recorddetail

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RecordDetailRoute(
    onClose: () -> Unit,
    recordDetailViewModel: RecordDetailViewModel = hiltViewModel()
) {
    BackHandler { onClose() }

    val uiState by recordDetailViewModel.uiState.collectAsState()

    RecordDetailScreen(
        uiState = uiState,
        onClose = onClose
    )
}
