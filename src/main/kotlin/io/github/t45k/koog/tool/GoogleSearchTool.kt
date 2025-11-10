package io.github.t45k.koog.tool

import ai.koog.agents.core.tools.Tool
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import org.jetbrains.annotations.VisibleForTesting

@Serializable
data class GoogleSearchArgs(val query: String, val num: Int = 5)

/**
 * Koog Tool that executes a Google Custom Search request and returns the raw JSON string response.
 *
 * Provide your own [HttpClient] if you need custom configuration or testing.
 */
class GoogleSearchTool(
    private val apiEndpoint: String = "https://www.googleapis.com/customsearch/v1",
    private val apiKey: String,
    private val cx: String,
    private val client: HttpClient = HttpClient(CIO)
) : Tool<GoogleSearchArgs, String>() {

    override val name: String = "googleSearch"
    override val description: String = "Execute web search and return a summary of top results. { query: string, num?: number }"

    override val argsSerializer: KSerializer<GoogleSearchArgs> = GoogleSearchArgs.serializer()
    override val resultSerializer: KSerializer<String> = String.serializer()

    override suspend fun execute(args: GoogleSearchArgs): String {
        val response: HttpResponse = client.get(apiEndpoint) {
            parameter("key", apiKey)
            parameter("cx", cx)
            parameter("q", args.query)
            parameter("num", args.num)
        }
        return response.bodyAsText()
    }

    @VisibleForTesting
    internal suspend fun executeForTest(args: GoogleSearchArgs): String = execute(args)
}
