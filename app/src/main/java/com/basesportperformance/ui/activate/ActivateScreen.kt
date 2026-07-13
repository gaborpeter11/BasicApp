package com.basesportperformance.ui.activate

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.basesportperformance.domain.model.ScratchCardState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivateScreen(
    cardState: ScratchCardState,
    lastError: String?,
    onActivate: (String) -> Unit,
    onClose: () -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text("Activate") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (cardState) {
                is ScratchCardState.Unscratched -> Text("Card not scratched yet. Go scratch first.")
                is ScratchCardState.Scratched -> {
                    val code = cardState.code
                    Text("Code to activate: $code")
                    Button(onClick = { onActivate(code) }) { Text("Activate") }
                }
                is ScratchCardState.Activated -> Text("Already activated")
                is ScratchCardState.Error -> Text("Error: ${cardState.message}")
            }

            lastError?.let { msg ->
                Spacer(Modifier.height(8.dp))
                Text("Error: $msg", color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.weight(1f))
            Button(onClick = onClose) { Text("Close") }
        }
    }
}
