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

___

## How to get a Google API key (Custom Search JSON API)

The steps below follow the official Programmable Search / Custom Search JSON API documentation.

1. Open Google Cloud Console
   - https://console.cloud.google.com/
   - Select an existing project or create a new one.

2. Enable the Custom Search JSON API
   - Open the API Library, search for "Custom Search API", and enable it.
   - Direct link: https://console.cloud.google.com/apis/library/customsearch.googleapis.com

3. Create credentials (issue an API key)
   - "APIs & Services" → "Credentials" → "Create credentials" → "API key".
   - Direct link: https://console.cloud.google.com/apis/credentials

4. Restrict your API key (recommended)
   - "Application restrictions": choose restrictions that fit your usage (HTTP referrers, IPs, Android/iOS, etc.).
   - "API restrictions": limit this key to "Custom Search API" only.

5. Review quotas
   - The free tier has a per‑day query limit (commonly 100 queries/day, subject to change).
   - See the documentation for up‑to‑date quota details.
   - Overview: https://developers.google.com/custom-search/v1/overview

Set the key as an environment variable named `GOOGLE_API_KEY` for convenience.

```bash
# macOS/Linux (bash/zsh)
export GOOGLE_API_KEY="YOUR_API_KEY"

# Windows (PowerShell)
$Env:GOOGLE_API_KEY = "YOUR_API_KEY"
```

---

## Create a Programmable Search Engine and find the `cx` (Search engine ID)

1. Open the Programmable Search console
   - https://programmablesearchengine.google.com/
   - All engines: https://programmablesearchengine.google.com/controlpanel/all
   - Legacy UI: https://cse.google.com/cse/all

2. Create a new search engine
   - Click "Create a new search engine".
   - Choose what to search: specific sites/domains, or (if eligible) the entire web.
   - After creation, go to the control panel (settings).

3. Find the `cx` (Search engine ID)
   - In the control panel, under Setup/Basic → Details, you’ll see the Search engine ID (`cx`).
   - Set this value as an environment variable `GOOGLE_CSE_CX`.

```bash
# macOS/Linux (bash/zsh)
export GOOGLE_CSE_CX="YOUR_CSE_ID"

# Windows (PowerShell)
$Env:GOOGLE_CSE_CX = "YOUR_CSE_ID"
```

4. Propagation note
   - Changes may take a few minutes to propagate after creation or updates.

---

## Quick sanity check

- Validate your API key and `cx`: try a simple curl and ensure you get a 200 response.

```bash
curl "https://www.googleapis.com/customsearch/v1?key=${GOOGLE_API_KEY}&cx=${GOOGLE_CSE_CX}&q=hello"
```

- If you see 403/400 errors:
  - The API key is wrong, or the Custom Search API isn’t enabled.
  - API restrictions don’t allow Custom Search API for this key.
  - The `cx` is wrong, you lack permission, or changes haven’t propagated yet.
