package com.basesportperformance.ui.recorddetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.basesportperformance.R
import com.basesportperformance.domain.model.SportsRecordSource
import com.basesportperformance.ui.common.CenteredStateCard
import com.basesportperformance.ui.common.SportIconBadge
import com.basesportperformance.ui.recorddetail.model.RecordDetailUiState
import com.basesportperformance.ui.theme.BaseSportPerformanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordDetailScreen(
    uiState: RecordDetailUiState,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.record_detail_title),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        when (uiState) {
            is RecordDetailUiState.Loading -> {
                CenteredStateCard(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            is RecordDetailUiState.NotFound -> {
                CenteredStateCard(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.height(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.record_detail_not_found_title),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.record_detail_not_found_message),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            is RecordDetailUiState.Success -> {
                RecordDetailContent(
                    uiState = uiState,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun RecordDetailContent(
    uiState: RecordDetailUiState.Success,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SportIconBadge(sport = uiState.type, size = 88.dp)

        Text(
            text = uiState.type,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = uiState.time,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = uiState.location,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_record_storage_title),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = when (uiState.source) {
                            SportsRecordSource.Local -> stringResource(R.string.sports_records_tab_local)
                            SportsRecordSource.Remote -> stringResource(R.string.sports_records_tab_remote)
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecordDetailSuccessPreview() {
    BaseSportPerformanceTheme {
        RecordDetailScreen(
            uiState = RecordDetailUiState.Success(
                type = "Running",
                time = "00:42:18",
                location = "City Park Track",
                source = SportsRecordSource.Local
            ),
            onClose = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordDetailLoadingPreview() {
    BaseSportPerformanceTheme {
        RecordDetailScreen(
            uiState = RecordDetailUiState.Loading,
            onClose = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordDetailNotFoundPreview() {
    BaseSportPerformanceTheme {
        RecordDetailScreen(
            uiState = RecordDetailUiState.NotFound,
            onClose = {}
        )
    }
}
