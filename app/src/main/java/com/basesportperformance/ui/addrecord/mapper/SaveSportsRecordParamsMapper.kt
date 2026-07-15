package com.basesportperformance.ui.addrecord.mapper

import com.basesportperformance.domain.model.SaveSportsRecordParams
import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.ui.addrecord.model.AddRecordUiState
import com.basesportperformance.ui.sportsrecords.model.SportsRecord

internal fun AddRecordUiState.toSaveSportsRecordParams() = SaveSportsRecordParams(
    sport = selectedSport.displayName,
    duration = duration,
    location = location,
    storeLocally = storeLocally
)

