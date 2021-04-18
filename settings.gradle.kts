rootProject.name = "scenarioPlatform"

pluginManagement {
    val kotlinPluginVersion: String by settings
    val springDependencyManagementPluginVersion: String by settings
    val springBootPluginVersion: String by settings

    val lombokPluginVersion: String by settings

    repositories {
        mavenLocal()
        gradlePluginPortal()
    }

    plugins {
        kotlin("multiplatform").version(kotlinPluginVersion)
        kotlin("jvm").version(kotlinPluginVersion)
        kotlin("js").version(kotlinPluginVersion)

        kotlin("plugin.spring").version(kotlinPluginVersion)

        id("io.spring.dependency-management").version(springDependencyManagementPluginVersion)
        id("org.springframework.boot").version(springBootPluginVersion)

        id("io.franzbecker.gradle-lombok").version(lombokPluginVersion)

    }

}

include(
    "common",
    "builder",
    "router",
    "model",
    "task",
    "config"
)