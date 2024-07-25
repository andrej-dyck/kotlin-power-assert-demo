import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.power-assert") version "2.0.0"
}

group = "andrej.dyck.kotlin.power.assert.demo"
version = "2.0.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // kotlin-test
    testImplementation(kotlin("test"))
    // AssertJ
    testImplementation("org.assertj:assertj-core:3.26.0")
}

/* Source sets by Kotlin conventions /src and /test */
sourceSets.main { kotlin.srcDirs("src/") }
sourceSets.test { kotlin.srcDirs("test/") }

/* Resources */
sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("test-resources")

/* Power-Assert */
@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
    functions = listOf(
        "kotlin.assert",
        "kotlin.require",
        "kotlin.test.assert",
        "kotlin.test.assertEquals",
        "kotlin.test.assertNotNull",
        "ad.demo.kotlin.powerassert.assertHasSku"
    )
    includedSourceSets = listOf("main", "test")
}

/* Check with JUnit 5 */
tasks.test {
    useJUnitPlatform {
        includeEngines("junit-jupiter")
        excludeEngines("junit-vintage")
    }
}

/* Gradle wrapper */
tasks.withType<Wrapper> {
    gradleVersion = "8.9"
    distributionType = Wrapper.DistributionType.BIN
}
