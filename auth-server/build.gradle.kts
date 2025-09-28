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
    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server:3.5.5")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}