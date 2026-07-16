package com.basesportperformance.ui.sportsrecords

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.basesportperformance.domain.model.SportsRecordSource
import com.basesportperformance.navigation.SportsRecords


internal fun NavGraphBuilder.sportsRecordsScreen(
    onNavigateToAddRecord: () -> Unit,
    onNavigateToRecordDetail: (String, SportsRecordSource) -> Unit
) {
    composable<SportsRecords> {
        SportsRecordsRoute(
            onNavigateToAddRecord = onNavigateToAddRecord,
            onNavigateToRecordDetail = onNavigateToRecordDetail
        )
    }
}


