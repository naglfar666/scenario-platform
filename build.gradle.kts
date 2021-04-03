
plugins {
    kotlin("multiplatform").apply(false)
    kotlin("jvm").apply(false)
    kotlin("js").apply(false)

    kotlin("plugin.spring").apply(false)

    id("io.spring.dependency-management").apply(false)
    id("org.springframework.boot").apply(false)

    id("io.franzbecker.gradle-lombok").apply(false)
}

subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
    }
}