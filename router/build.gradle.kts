val commonsCliVersion: String by project
val commonsLangVersion: String by project
val jacksonDatabindVersion: String by project
val lombokVersion: String by project
val springDataRedisVersion: String by project
val jedisVersion: String by project

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
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.kafka:spring-kafka")
    api("commons-cli:commons-cli:${commonsCliVersion}")
    api("org.apache.commons:commons-lang3:${commonsLangVersion}")
    api("com.fasterxml.jackson.core:jackson-databind:${jacksonDatabindVersion}")
    api("org.springframework.data:spring-data-redis:${springDataRedisVersion}")
    api("redis.clients:jedis:${jedisVersion}")
//    api("org.apache.commons:commons-pool2")

    api(project(":model"))
    api(project(":config"))
    api(project(":common"))

    compileOnly("org.projectlombok:lombok:${lombokVersion}")

}

application {
    mainClassName = "org.example.router.RouterApplication"
}