package com.basesportperformance.data

interface ApiService {
    suspend fun getVersion(code: String): VersionResponse
}
