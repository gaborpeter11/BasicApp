package com.example.basicapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class HttpApiService(
    private val baseUrl: String = "https://api.o2.sk/",
    private val client: OkHttpClient = OkHttpClient()
) : ApiService {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getVersion(code: String): VersionResponse = withContext(Dispatchers.IO) {
        val encoded = URLEncoder.encode(code, StandardCharsets.UTF_8.toString())

        val request = Request.Builder()
            .url("${baseUrl}api/version?code=$encoded")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("HTTP ${response.code}: ${response.message}")
            }

            val body = response.body?.string()
                ?: throw IOException("Empty response body")

            try {
                json.decodeFromString<VersionResponse>(body)
            } catch (e: Exception) {
                throw IOException("Failed to parse response: ${e.message}", e)
            }
        }
    }
}
