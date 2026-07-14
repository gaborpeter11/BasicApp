package com.basesportperformance.ui.sportsrecords

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.basesportperformance.navigation.SportsRecords


internal fun NavGraphBuilder.sportsRecordsScreen(
    onNavigateToAddRecord: () -> Unit
) {
    composable<SportsRecords> {
        SportsRecordsRoute(
            onNavigateToAddRecord = onNavigateToAddRecord
        )
    }
}


