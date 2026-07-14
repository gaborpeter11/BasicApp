package com.basesportperformance.ui.sportsrecords.mapper

import com.basesportperformance.domain.model.SportsRecordDo
import com.basesportperformance.ui.sportsrecords.model.SportsRecord

internal fun SportsRecordDo.toUiModel(): SportsRecord = SportsRecord(
    id = id,
    name = name,
    time = time,
    type = type,
    source = source
)

