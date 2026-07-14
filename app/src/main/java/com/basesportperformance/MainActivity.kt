package com.basesportperformance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.basesportperformance.navigation.AppNavHost
import com.basesportperformance.navigation.Scratch
import com.basesportperformance.navigation.SportsRecords
import com.basesportperformance.ui.activate.activateScreen
import com.basesportperformance.ui.sportsrecords.sportsRecordsScreen
import com.basesportperformance.ui.scratch.scratchScreen
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
                        onNavigateToScratch = {
                            navController.navigate(Scratch)
                        }
                    )

                    activateScreen(
                        onClose = { navController.popBackStack() }
                    )

                    scratchScreen(
                        onClose = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}