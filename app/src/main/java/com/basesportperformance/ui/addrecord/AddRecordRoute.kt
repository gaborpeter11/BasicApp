package com.basesportperformance.ui.addrecord

import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.basesportperformance.R

@Composable
fun AddRecordRoute(
    onClose: () -> Unit,
    addRecordViewModel: AddRecordViewModel = hiltViewModel()
) {
    BackHandler { onClose() }

    val uiState by addRecordViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val saveErrorMessage = stringResource(R.string.add_record_save_error)

    LaunchedEffect(addRecordViewModel) {
        addRecordViewModel.navigationEvents.collect {
            onClose()
        }
    }

    LaunchedEffect(addRecordViewModel, saveErrorMessage) {
        addRecordViewModel.errorEvents.collect {
            snackbarHostState.showSnackbar(saveErrorMessage)
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
        onClose = onClose,
        snackbarHostState = snackbarHostState
    )
}
