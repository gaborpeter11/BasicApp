package com.basesportperformance.domain.usecase

import com.basesportperformance.data.SportsRecordsRepository
import javax.inject.Inject

class SeedSportsRecordsUseCase @Inject constructor(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    suspend operator fun invoke() {
        sportsRecordsRepository.seedSampleRecordsIfNeeded()
    }
}

