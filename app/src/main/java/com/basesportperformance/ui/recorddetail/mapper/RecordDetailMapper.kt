package com.basesportperformance.ui.recorddetail.mapper

import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.ui.recorddetail.model.RecordDetailUiState

internal fun SportsRecordDto.toDetailUiState(): RecordDetailUiState.Success = RecordDetailUiState.Success(
    type = type,
    time = time,
    location = location,
    source = source
)
