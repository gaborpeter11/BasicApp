package com.example.basicapp.data

interface ApiService {
    suspend fun getVersion(code: String): VersionResponse
}
