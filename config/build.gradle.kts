val commonsCliVersion: String by project
val commonsLangVersion: String by project
val jacksonDatabindVersion: String by project
val lombokVersion: String by project

plugins {
    kotlin("jvm")

    java
    id("io.spring.dependency-management")
    id("org.springframework.boot")

    id("io.franzbecker.gradle-lombok")

    application
    idea
}

tasks {
    jar {
        enabled = true
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter")
    api("org.springframework.kafka:spring-kafka")

    compileOnly("org.projectlombok:lombok:${lombokVersion}")

}

application {
    mainClassName = "org.example.config.ConfigApplication"
}