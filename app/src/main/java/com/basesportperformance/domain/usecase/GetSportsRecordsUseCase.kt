package com.basesportperformance.domain.usecase

import com.basesportperformance.data.SportsRecordsRepository
import javax.inject.Inject

class GetSportsRecordsUseCase @Inject constructor(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    suspend operator fun invoke() = sportsRecordsRepository.getSportsRecords()
}

