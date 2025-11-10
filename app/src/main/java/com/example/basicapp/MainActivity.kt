package com.example.basicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.basicapp.navigation.Activate
import com.example.basicapp.navigation.AppNavHost
import com.example.basicapp.navigation.Main
import com.example.basicapp.navigation.Scratch
import com.example.basicapp.ui.activate.activateScreen
import com.example.basicapp.ui.main.mainScreen
import com.example.basicapp.ui.scratch.scratchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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