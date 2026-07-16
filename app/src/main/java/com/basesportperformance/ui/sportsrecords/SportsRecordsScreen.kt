package com.basesportperformance.ui.sportsrecords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.basesportperformance.ui.sportsrecords.model.SportsRecord
import com.basesportperformance.ui.sportsrecords.model.SportsRecordsAction
import com.basesportperformance.ui.sportsrecords.model.SportsRecordsTab
import com.basesportperformance.ui.sportsrecords.model.SportsRecordsUiState
import com.basesportperformance.ui.sportsrecords.model.previewSportsRecords
import com.basesportperformance.ui.theme.BaseSportPerformanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsRecordsScreen(
    uiState: SportsRecordsUiState,
    onAction: (SportsRecordsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedTabIndex = SportsRecordsTab.entries.indexOf(uiState.selectedTab)

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.sports_records_title),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        floatingActionButton = {
            if (uiState is SportsRecordsUiState.Success) {
                ExtendedFloatingActionButton(
                    onClick = { onAction(SportsRecordsAction.AddRecord) },
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    icon = {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                    },
                    text = {
                        Text(stringResource(R.string.sports_records_add_record))
                    }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                SportsRecordsTab.entries.forEachIndexed { index, tab ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { onAction(SportsRecordsAction.TabSelected(tab)) },
                        text = { Text(stringResource(tab.titleRes)) }
                    )
                }
            }

            when (uiState) {
                is SportsRecordsUiState.Loading -> {
                    LoadingState(modifier = Modifier.fillMaxSize())
                }

                is SportsRecordsUiState.Error -> {
                    ErrorState(
                        modifier = Modifier.fillMaxSize(),
                        message = uiState.message,
                        onRetryClick = { onAction(SportsRecordsAction.Retry) }
                    )
                }

                is SportsRecordsUiState.Empty -> {
                    EmptyState(
                        modifier = Modifier.fillMaxSize(),
                        onAddRecordClick = { onAction(SportsRecordsAction.AddRecord) }
                    )
                }

                is SportsRecordsUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            SportsSummaryCard(
                                records = uiState.records,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        items(
                            items = uiState.records,
                            key = { record -> record.id }
                        ) { record ->
                            SportsRecordCard(
                                modifier = Modifier.fillMaxWidth(),
                                record = record,
                                onClick = {
                                    onAction(SportsRecordsAction.RecordClicked(record.id, record.source))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SportsSummaryCard(
    records: List<SportsRecord>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryStat(
                label = stringResource(R.string.sports_records_summary_count),
                value = records.size.toString()
            )
            SummaryStat(
                label = stringResource(R.string.sports_records_summary_time),
                value = records.totalDurationLabel()
            )
        }
    }
}

@Composable
private fun SummaryStat(
    label: String,
    value: String
) {
    Column {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )
    }
}

private fun List<SportsRecord>.totalDurationLabel(): String {
    val totalSeconds = sumOf { it.time.toDurationSecondsOrZero() }
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d:%02d".format(hours, minutes, seconds)
}

private fun String.toDurationSecondsOrZero(): Int {
    val parts = split(':').mapNotNull { it.toIntOrNull() }
    if (parts.size != 3) return 0
    return parts[0] * 3600 + parts[1] * 60 + parts[2]
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier
) {
    CenteredStateCard(modifier = modifier) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.sports_records_loading_title),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.sports_records_loading_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier,
    onAddRecordClick: () -> Unit
) {
    CenteredStateCard(modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.SportsScore,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.height(48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.sports_records_empty_title),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.sports_records_empty_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddRecordClick) {
            Text(stringResource(R.string.sports_records_add_record))
        }
    }
}

@Composable
private fun ErrorState(
    modifier: Modifier = Modifier,
    message: String,
    onRetryClick: () -> Unit
) {
    CenteredStateCard(modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.ErrorOutline,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.height(48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.sports_records_error_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = onRetryClick) {
                Text(stringResource(R.string.sports_records_retry))
            }
        }
    }
}

@Composable
private fun SportsRecordCard(
    modifier: Modifier = Modifier,
    record: SportsRecord,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SportIconBadge(sport = record.type)

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.type,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = when (record.source) {
                        SportsRecordSource.Local -> stringResource(R.string.sports_records_tab_local)
                        SportsRecordSource.Remote -> stringResource(R.string.sports_records_tab_remote)
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = record.time,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SportsRecordsSuccessPreview() {
    BaseSportPerformanceTheme {
        SportsRecordsScreen(
            uiState = SportsRecordsUiState.Success(
                records = previewSportsRecords,
                selectedTab = SportsRecordsTab.All
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SportsRecordsLoadingPreview() {
    BaseSportPerformanceTheme {
        SportsRecordsScreen(
            uiState = SportsRecordsUiState.Loading(
                selectedTab = SportsRecordsTab.Local
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SportsRecordsEmptyPreview() {
    BaseSportPerformanceTheme {
        SportsRecordsScreen(
            uiState = SportsRecordsUiState.Empty(
                selectedTab = SportsRecordsTab.Remote
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SportsRecordsErrorPreview() {
    BaseSportPerformanceTheme {
        SportsRecordsScreen(
            uiState = SportsRecordsUiState.Error(
                message = "Unable to load records right now.",
                selectedTab = SportsRecordsTab.All
            ),
            onAction = {}
        )
    }
}