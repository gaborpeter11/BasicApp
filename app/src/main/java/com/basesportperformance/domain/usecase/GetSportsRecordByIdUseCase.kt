package com.basesportperformance.domain.usecase

import com.basesportperformance.data.SportsRecordsRepository
import com.basesportperformance.domain.model.SportsRecordSource
import javax.inject.Inject

class GetSportsRecordByIdUseCase @Inject constructor(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    operator fun invoke(id: String, source: SportsRecordSource) =
        sportsRecordsRepository.observeSportsRecord(id, source)
}
