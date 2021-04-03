val lombokVersion: String by project

plugins {
    kotlin("jvm")

    id("io.spring.dependency-management")

    id("io.franzbecker.gradle-lombok")

    idea
}

dependencies {
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
}

tasks {
    jar {
        enabled = true
    }
}