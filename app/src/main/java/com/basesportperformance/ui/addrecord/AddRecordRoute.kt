package com.basesportperformance.ui.addrecord

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddRecordRoute(
    onClose: () -> Unit,
    addRecordViewModel: AddRecordViewModel = hiltViewModel()
) {
    BackHandler { onClose() }

    val uiState by addRecordViewModel.uiState.collectAsState()

    LaunchedEffect(addRecordViewModel) {
        addRecordViewModel.navigationEvents.collect {
            onClose()
        }
    }

    AddRecordScreen(
        uiState = uiState,
        onSportSelected = addRecordViewModel::onSportSelected,
        onLocationChanged = addRecordViewModel::onLocationChanged,
        onUseCurrentLocation = addRecordViewModel::onUseCurrentLocation,
        onDurationChanged = addRecordViewModel::onDurationChanged,
        onStoreLocallyChanged = addRecordViewModel::onStoreLocallyChanged,
        onAddRecord = addRecordViewModel::addRecord,
        onClose = onClose
    )
}

