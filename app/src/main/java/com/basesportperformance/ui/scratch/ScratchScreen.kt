package com.basesportperformance.ui.scratch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.basesportperformance.domain.model.ScratchCardState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScratchScreen(
    cardState: ScratchCardState,
    isScratching: Boolean,
    onScratch: () -> Unit,
    onClose: () -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text("Scratch") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (cardState) {
                is ScratchCardState.Unscratched -> Text("Card is unscratched")
                is ScratchCardState.Scratched -> Text("Code: ${cardState.code}")
                is ScratchCardState.Activated -> Text("Card activated")
                is ScratchCardState.Error -> Text("Error: ${cardState.message}")
            }

            if (isScratching) {
                CircularProgressIndicator()
                Text("Scratching... (will take ~2s). If you close, operation cancels.")
            } else {
                Button(onClick = onScratch) { Text("Start scratch") }
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onClose) { Text("Close") }
        }
    }
}
