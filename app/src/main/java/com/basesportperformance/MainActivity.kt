package com.basesportperformance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.basesportperformance.navigation.AppNavHost
import com.basesportperformance.navigation.AddRecord
import com.basesportperformance.navigation.SportsRecords
import com.basesportperformance.ui.activate.activateScreen
import com.basesportperformance.ui.addrecord.addRecordScreen
import com.basesportperformance.ui.sportsrecords.sportsRecordsScreen
import com.basesportperformance.ui.theme.BaseSportPerformanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseSportPerformanceTheme {
                val navController = rememberNavController()
                val startDestination = SportsRecords

                AppNavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    sportsRecordsScreen(
                        onNavigateToAddRecord = {
                            navController.navigate(AddRecord)
                        }
                    )

                    activateScreen(
                        onClose = { navController.popBackStack() }
                    )

                    addRecordScreen(
                        onClose = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}