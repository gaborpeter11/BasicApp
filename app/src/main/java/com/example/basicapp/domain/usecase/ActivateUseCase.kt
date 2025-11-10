package com.example.basicapp.domain.usecase

import com.example.basicapp.data.ApiService
import javax.inject.Inject


class ActivateUseCase @Inject constructor(
    private val apiService: ApiService
) {

    suspend operator fun invoke(code: String): Boolean {
        val resp = apiService.getVersion(code)
        return resp.android > 277028L
    }
}
