package com.basesportperformance.domain.usecase

import com.basesportperformance.data.SportsRecordsRepository
import javax.inject.Inject

class GetSportsRecordByIdUseCase @Inject constructor(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    operator fun invoke(id: Long) = sportsRecordsRepository.observeSportsRecord(id)
}
