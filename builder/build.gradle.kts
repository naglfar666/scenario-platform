val camundaBpmnModelVersion: String by project
val commonsCliVersion: String by project
val commonsLangVersion: String by project
val jacksonDatabindVersion: String by project
val lombokVersion: String by project

plugins {
    kotlin("jvm")

    kotlin("plugin.spring")

    java
    id("io.spring.dependency-management")
    id("org.springframework.boot")

    id("io.franzbecker.gradle-lombok")

    application
    idea
}

dependencies {
    api("org.springframework.boot:spring-boot-starter")
    api("org.camunda.bpm.model:camunda-bpmn-model:${camundaBpmnModelVersion}")
    api("commons-cli:commons-cli:${commonsCliVersion}")
    api("org.apache.commons:commons-lang3:${commonsLangVersion}")
    api("com.fasterxml.jackson.core:jackson-databind:${jacksonDatabindVersion}")

    api(project(":model"))

    compileOnly("org.projectlombok:lombok:${lombokVersion}")

}

tasks {
    jar {
        enabled = true
    }
}

application {
    mainClassName = "org.example.builder.Application"
}