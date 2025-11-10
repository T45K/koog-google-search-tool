package io.github.t45k.koog.tool

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.http.headersOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GoogleSearchToolTest {

    @Test
    suspend fun `execute should call Google Custom Search with params and return body`() {
        val expectedBody = """{"items":[{"title":"Kotlin"}]}"""
        val apiKey = "TEST_KEY"
        val cx = "TEST_CX"
        val query = "kotlin"
        val num = 3

        val mockEngine = MockEngine { request: HttpRequestData ->
            val url: Url = request.url
            assertEquals("https", url.protocol.name)
            assertEquals("www.googleapis.com", url.host)
            assertEquals("/customsearch/v1", url.encodedPath)
            assertEquals(apiKey, url.parameters["key"])
            assertEquals(cx, url.parameters["cx"])
            assertEquals(query, url.parameters["q"])
            assertEquals(num.toString(), url.parameters["num"])

            respond(
                content = expectedBody,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine)
        val tool = GoogleSearchTool(apiKey = apiKey, cx = cx, client = client)

        val result = tool.executeForTest(GoogleSearchArgs(query = query, num = num))

        assertEquals(expectedBody, result)
    }
}
