package com.basesportperformance.ui.addrecord

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.basesportperformance.R
import com.basesportperformance.ui.addrecord.model.AddRecordUiState
import com.basesportperformance.ui.addrecord.model.SportType
import com.basesportperformance.ui.common.color
import com.basesportperformance.ui.common.icon
import com.basesportperformance.ui.theme.BaseSportPerformanceTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordScreen(
    uiState: AddRecordUiState,
    onSportSelected: (SportType) -> Unit,
    onLocationChanged: (String) -> Unit,
    onUseCurrentLocation: () -> Unit,
    onDurationChanged: (String) -> Unit,
    onStoreLocallyChanged: (Boolean) -> Unit,
    onAddRecord: () -> Unit,
    onClose: () -> Unit
) {

    val durationFormat = "%02d:%02d:%02d"

    var isSportExpanded by rememberSaveable { mutableStateOf(false) }
    var showDurationPicker by rememberSaveable { mutableStateOf(false) }
    var selectedHours by rememberSaveable { mutableIntStateOf(0) }
    var selectedMinutes by rememberSaveable { mutableIntStateOf(0) }
    var selectedSeconds by rememberSaveable { mutableIntStateOf(0) }

    fun openDurationPicker() {
        val durationParts = uiState.duration.toDurationParts()
        selectedHours = durationParts.hours
        selectedMinutes = durationParts.minutes
        selectedSeconds = durationParts.seconds
        showDurationPicker = true
    }

    if (showDurationPicker) {
        DurationPickerDialog(
            hours = selectedHours,
            minutes = selectedMinutes,
            seconds = selectedSeconds,
            onHoursSelected = { selectedHours = it },
            onMinutesSelected = { selectedMinutes = it },
            onSecondsSelected = { selectedSeconds = it },
            onDismiss = { showDurationPicker = false },
            onConfirm = {
                onDurationChanged(
                    String.format(
                        Locale.getDefault(),
                        durationFormat,
                        selectedHours,
                        selectedMinutes,
                        selectedSeconds
                    )
                )
                showDurationPicker = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_record_title),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = onAddRecord,
                enabled = uiState.isAddRecordEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.sports_records_add_record),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.add_record_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start
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
                    ExposedDropdownMenuBox(
                        expanded = isSportExpanded,
                        onExpandedChange = { expanded -> isSportExpanded = expanded }
                    ) {
                        OutlinedTextField(
                            value = uiState.selectedSport.label(),
                            onValueChange = {},
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            readOnly = true,
                            label = {
                                Text(stringResource(R.string.add_record_sport_label))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = uiState.selectedSport.icon(),
                                    contentDescription = null,
                                    tint = uiState.selectedSport.color()
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSportExpanded)
                            },
                            singleLine = true
                        )

                        DropdownMenu(
                            expanded = isSportExpanded,
                            onDismissRequest = { isSportExpanded = false }
                        ) {
                            SportType.entries.forEach { sport ->
                                DropdownMenuItem(
                                    text = { Text(sport.label()) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = sport.icon(),
                                            contentDescription = null,
                                            tint = sport.color()
                                        )
                                    },
                                    onClick = {
                                        onSportSelected(sport)
                                        isSportExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = uiState.location,
                        onValueChange = onLocationChanged,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(stringResource(R.string.add_record_location_label))
                        },
                        singleLine = true,
                        trailingIcon = {
                            IconButton(
                                onClick = onUseCurrentLocation
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.LocationOn,
                                    contentDescription = "Use current location"
                                )
                            }
                        }
                    )

                    OutlinedTextField(
                        value = uiState.duration,
                        onValueChange = onDurationChanged,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(stringResource(R.string.add_record_duration_label))
                        },
                        singleLine = true,
                        trailingIcon = {
                            IconButton(
                                onClick = ::openDurationPicker
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Schedule,
                                    contentDescription = "Select duration"
                                )
                            }
                        }
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = if (uiState.storeLocally) {
                            Icons.Filled.PhoneAndroid
                        } else {
                            Icons.Filled.CloudQueue
                        },
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = stringResource(R.string.add_record_storage_title),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = if (uiState.storeLocally) {
                                stringResource(R.string.add_record_storage_local_description)
                            } else {
                                stringResource(R.string.add_record_storage_remote_description)
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = !uiState.storeLocally,
                        onCheckedChange = { isOnlineStorageEnabled ->
                            onStoreLocallyChanged(!isOnlineStorageEnabled)
                        },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                            checkedThumbColor = MaterialTheme.colorScheme.onTertiary
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DurationPickerDialog(
    hours: Int,
    minutes: Int,
    seconds: Int,
    onHoursSelected: (Int) -> Unit,
    onMinutesSelected: (Int) -> Unit,
    onSecondsSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_record_duration_picker_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                DurationValueSelector(
                    label = stringResource(R.string.add_record_hours_label),
                    value = hours,
                    range = 0..23,
                    onValueSelected = onHoursSelected
                )
                DurationValueSelector(
                    label = stringResource(R.string.add_record_minutes_label),
                    value = minutes,
                    range = 0..59,
                    onValueSelected = onMinutesSelected
                )
                DurationValueSelector(
                    label = stringResource(R.string.add_record_seconds_label),
                    value = seconds,
                    range = 0..59,
                    onValueSelected = onSecondsSelected
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.add_record_duration_picker_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.back))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DurationValueSelector(
    label: String,
    value: Int,
    range: IntRange,
    onValueSelected: (Int) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { isExpanded -> expanded = isExpanded }
    ) {
        OutlinedTextField(
            value = value.formatDurationPart(),
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            singleLine = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            range.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.formatDurationPart()) },
                    onClick = {
                        onValueSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun SportType.label(): String = when (this) {
    SportType.RUNNING -> stringResource(R.string.add_record_sport_running)
    SportType.CYCLING -> stringResource(R.string.add_record_sport_cycling)
    SportType.SWIMMING -> stringResource(R.string.add_record_sport_swimming)
    SportType.HIKING -> stringResource(R.string.add_record_sport_hiking)
    SportType.GYM -> stringResource(R.string.add_record_sport_gym)
    SportType.OTHER -> stringResource(R.string.add_record_sport_other)
}

private fun String.toDurationParts(): DurationParts {
    val parts = split(':')

    if (parts.size != 3) return DurationParts()

    return DurationParts(
        hours = parts[0].toIntOrNull() ?: 0,
        minutes = parts[1].toIntOrNull() ?: 0,
        seconds = parts[2].toIntOrNull() ?: 0
    )
}

private fun Int.formatDurationPart(): String = toString().padStart(2, '0')

private data class DurationParts(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0
)


@Preview(
    name = "Default",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AddRecordScreenPreview() {
    BaseSportPerformanceTheme {
        AddRecordScreen(
            uiState = AddRecordUiState(),
            onSportSelected = {},
            onLocationChanged = {},
            onUseCurrentLocation = {},
            onDurationChanged = {},
            onStoreLocallyChanged = {},
            onAddRecord = {},
            onClose = {}
        )
    }
}

@Preview(name = "Disabled", showBackground = true)
@Composable
private fun AddRecordScreenDisabledPreview() {
    BaseSportPerformanceTheme {
        AddRecordScreen(
            uiState = AddRecordUiState(
                location = "",
                duration = ""
            ),
            onSportSelected = {},
            onLocationChanged = {},
            onUseCurrentLocation = {},
            onDurationChanged = {},
            onStoreLocallyChanged = {},
            onAddRecord = {},
            onClose = {}
        )
    }
}