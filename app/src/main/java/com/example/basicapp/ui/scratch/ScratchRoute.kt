package com.example.basicapp.ui.scratch

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ScratchRoute(
    onClose: () -> Unit,
    scratchViewModel: ScratchViewModel = hiltViewModel()
) {
    BackHandler {
        onClose()
    }

    val cardState by scratchViewModel.cardState.collectAsState()
    val scratchScreenState by scratchViewModel.scratchScreenState.collectAsState()

    ScratchScreen(
        cardState = cardState,
        isScratching = scratchScreenState.loading,
        onScratch = scratchViewModel::scratch,
        onClose = onClose
    )
}
