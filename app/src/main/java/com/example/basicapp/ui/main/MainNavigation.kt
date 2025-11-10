package com.example.basicapp.ui.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.basicapp.navigation.Main


internal fun NavGraphBuilder.mainScreen(
    onNavigateToScratch: () -> Unit,
    onNavigateToActivate: () -> Unit
) {
    composable<Main> {

        MainRoute(
            onNavigateToScratch = onNavigateToScratch,
            onNavigateToActivate = onNavigateToActivate
        )
    }
}
