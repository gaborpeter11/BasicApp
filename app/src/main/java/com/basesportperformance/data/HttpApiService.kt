package com.basesportperformance.data

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException


class HttpApiService(
    private val baseUrl: String = "https://api.o2.sk/",
    private val client: OkHttpClient = OkHttpClient()
) : ApiService {

    override suspend fun getVersion(code: String): VersionResponse = withContext(Dispatchers.IO) {
        val encodedCode = Uri.encode(code)
        val url = "${baseUrl.trimEnd('/')}/version?code=$encodedCode"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("HTTP ${response.code}: ${response.message}")
            }

            val body = response.body?.string()
                ?: throw IOException("Empty response body")

            val json = JSONObject(body)
            val androidValue = json.optLong("android", -1L)

            if (androidValue == -1L) {
                throw IOException("Missing field 'android' in response")
            }

            VersionResponse(androidValue)
        }
    }
}
