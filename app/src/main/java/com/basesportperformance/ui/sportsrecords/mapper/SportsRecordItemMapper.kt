package com.basesportperformance.ui.sportsrecords.mapper

import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.ui.sportsrecords.model.SportsRecord

internal fun SportsRecordDto.toUiModel(): SportsRecord = SportsRecord(
    id = id,
    name = name,
    time = time,
    type = type,
    source = source
)

