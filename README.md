# Google Search Tool for Koog

A small Kotlin JVM library that provides a Koog `Tool` wrapping Google Custom Search (CSE) via Ktor HTTP client.

## Usage

```kotlin
suspend fun main() {
    val googleSearchTool = GoogleSearchTool(
        apiKey = System.getenv("GOOGLE_API_KEY"),
        cx = System.getenv("GOOGLE_CSE_CX")
    )

    AIAgent(
        yourPromptExecutor,
        yourLlmModel,
        yourStrategy,
        ToolRegistry {
            tool(googleSearchTool)
        },
        systemPrompt = "Your prompt",
    ).execute()
}
```

You can also pass a custom `HttpClient` (e.g., to set timeouts or proxies).
