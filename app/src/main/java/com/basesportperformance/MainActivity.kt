package com.basesportperformance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.basesportperformance.navigation.Activate
import com.basesportperformance.navigation.AppNavHost
import com.basesportperformance.navigation.Main
import com.basesportperformance.navigation.Scratch
import com.basesportperformance.ui.activate.activateScreen
import com.basesportperformance.ui.main.mainScreen
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
                val startDestination = Main

                AppNavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    mainScreen(
                        onNavigateToScratch = {
                            navController.navigate(Scratch)
                        },
                        onNavigateToActivate = {
                            navController.navigate(Activate)
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