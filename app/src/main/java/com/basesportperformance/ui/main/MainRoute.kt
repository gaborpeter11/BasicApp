package com.basesportperformance.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainRoute(
    onNavigateToScratch: () -> Unit,
    onNavigateToActivate: () -> Unit,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val cardState by mainViewModel.cardState.collectAsState()

    MainScreen(
        cardState = cardState,
        onScratchClick = onNavigateToScratch,
        onActivateClick = onNavigateToActivate
    )
}