package com.example.basicapp.ui.activate

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basicapp.domain.model.ScratchCardState

@Composable
fun ActivateRoute(
    onClose: () -> Unit,
    activateViewModel: ActivateViewModel = hiltViewModel()
) {
    BackHandler { onClose() }

    val cardState by activateViewModel.cardState.collectAsState()

    val lastError = (cardState as? ScratchCardState.Error)?.message

    ActivateScreen(
        cardState = cardState,
        lastError = lastError,
        onActivate = activateViewModel::activateInBackground,
        onClose = onClose
    )
}
