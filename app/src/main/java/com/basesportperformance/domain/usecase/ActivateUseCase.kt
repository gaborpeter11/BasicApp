package com.basesportperformance.domain.usecase

import com.basesportperformance.data.ApiService
import javax.inject.Inject

class ActivateUseCase @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(code: String): Boolean {
        val response = apiService.getVersion(code)
        return response.android > 277028L
    }
}
