package com.example.basicapp.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.basicapp.domain.model.ScratchCardState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    cardState: ScratchCardState,
    onScratchClick: () -> Unit,
    onActivateClick: () -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text("ScratchCard App") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Current card state:")
            when (cardState) {
                is ScratchCardState.Unscratched -> Text("UNSCRATCHED")
                is ScratchCardState.Scratched -> Text("SCRATCHED — code: ${cardState.code}")
                is ScratchCardState.Activated -> Text("ACTIVATED")
                is ScratchCardState.Error -> Text("ERROR: ${cardState.message}")
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = onScratchClick) { Text("Go to Scratch") }
                Button(onClick = onActivateClick) { Text("Go to Activate") }
            }
        }
    }
}
