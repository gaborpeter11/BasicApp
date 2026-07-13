package com.basesportperformance.domain.usecase

import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject


class GetScratchCodeUseCase @Inject constructor() {
    suspend operator fun invoke(): String {
        delay(2_000)
        return UUID.randomUUID().toString()
    }
}
