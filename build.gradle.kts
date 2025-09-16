plugins {
    kotlin("jvm") version "2.2.0"
    id("org.springframework.boot") version "3.4.5"
}

group = "fi.kmpapps"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.ai:spring-ai-starter-mcp-server-webmvc:1.0.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}