package com.basesportperformance.domain.usecase

import com.basesportperformance.data.SportsRecordsRepository
import com.basesportperformance.domain.model.SaveSportsRecordParams
import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.domain.model.SportsRecordSource
import javax.inject.Inject

class SaveSportsRecordUseCase @Inject constructor(
    private val sportsRecordsRepository: SportsRecordsRepository
) {
    suspend operator fun invoke(params: SaveSportsRecordParams) {
        sportsRecordsRepository.saveSportsRecord(
            SportsRecordDto(
                id = "",
                location = params.location,
                time = params.duration,
                type = params.sport,
                source = if (params.storeLocally) {
                    SportsRecordSource.Local
                } else {
                    SportsRecordSource.Remote
                }
            )
        )
    }
}

