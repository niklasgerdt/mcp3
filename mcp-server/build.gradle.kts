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
    implementation("org.springframework.ai:spring-ai-starter-mcp-server-webmvc:1.0.2")
    implementation("org.springframework.boot:spring-boot-starter-security:3.5.5")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.5.5")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:3.5.5")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.5.5")
//    implementation("org.springframework.security:spring-security-web:6.5.5")
//    implementation("org.springframework")
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.security:spring-security-test:6.5.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.5.5")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}