//package com.basesportperformance
//
//import com.basesportperformance.data.HttpApiService
//import kotlinx.coroutines.test.runTest
//import okhttp3.OkHttpClient
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import kotlin.test.assertEquals
//
//class HttpApiServiceTest {
//
//    private lateinit var server: MockWebServer
//    private lateinit var service: HttpApiService
//
//    @Before
//    fun setup() {
//        server = MockWebServer()
//        server.start()
//
//        service = HttpApiService(
//            baseUrl = server.url("/").toString(),
//            client = OkHttpClient()
//        )
//    }
//
//    @After
//    fun teardown() {
//        server.shutdown()
//    }
//
//    @Test
//    fun `getVersion returns correct android value`() = runTest {
//        val expectedValue = 287028L
//        val jsonResponse = """{"android": $expectedValue}"""
//
//        server.enqueue(MockResponse().setResponseCode(200).setBody(jsonResponse))
//
//        val result = service.getVersion("someCode")
//
//        assertEquals(expectedValue, result.android)
//    }
//
//    @Test(expected = java.io.IOException::class)
//    fun `getVersion throws when response body missing field`() = runTest {
//        server.enqueue(MockResponse().setResponseCode(200).setBody("""{"ios":123}"""))
//
//        service.getVersion("someCode")
//    }
//
//    @Test(expected = java.io.IOException::class)
//    fun `getVersion throws on HTTP error`() = runTest {
//        server.enqueue(MockResponse().setResponseCode(500).setBody("Internal Server Error"))
//        service.getVersion("someCode")
//    }
//}
