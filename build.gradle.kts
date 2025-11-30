plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "io.github.t45k"
version = "0.0.1"

kotlin {
    jvmToolchain(25)
}

dependencies {
    // Koog tool API
    api(libs.koog.agents.tools)

    // Ktor HTTP client
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.json)

    // Logger
    runtimeOnly(libs.logback.classic)

    // Test
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.ktor.client.mock)
}

tasks.test {
    useJUnitPlatform()
}
