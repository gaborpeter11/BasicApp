package com.basesportperformance.ui.sportsrecords

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.basesportperformance.navigation.SportsRecords


internal fun NavGraphBuilder.sportsRecordsScreen(
    onNavigateToScratch: () -> Unit
) {
    composable<SportsRecords> {
        SportsRecordsRoute(
            onNavigateToScratch = onNavigateToScratch
        )
    }
}


