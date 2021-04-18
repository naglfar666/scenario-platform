val lombokVersion: String by project
val jacksonDatabindVersion: String by project

plugins {
    kotlin("jvm")

    kotlin("plugin.spring")

    java

    id("io.spring.dependency-management")

    id("io.franzbecker.gradle-lombok")

    idea
}

dependencies {
    api("com.fasterxml.jackson.core:jackson-databind:${jacksonDatabindVersion}")
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
}

tasks {
    jar {
        enabled = true
    }
}