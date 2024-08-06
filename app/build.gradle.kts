import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    id ("jacoco")
    id ("checkstyle")
    id("io.freefair.lombok") version "8.6"
    id("com.github.johnrengelman.shadow") version "8.1.1"

}


group = "hexlet.code"
version = "1.0-SNAPSHOT"
application {
    mainClass.set("hexlet.code.App")
}
jacoco {
    toolVersion = "0.8.9"
    reportsDirectory = layout.buildDirectory.dir("reports/jacoco")
}
repositories {
    mavenCentral()
}

dependencies {
    implementation("gg.jte:jte:3.1.9")
    implementation("io.javalin:javalin:6.1.3")
    implementation("io.javalin:javalin-bundle:6.1.3")
    implementation("io.javalin:javalin-rendering:6.1.3")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.220")
    implementation("org.slf4j:slf4j-simple:2.0.10")
    testImplementation("org.slf4j:slf4j-simple:2.0.13")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    // https://technology.lastminute.com/junit5-kotlin-and-gradle-dsl/
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        // showStackTraces = true
        // showCauses = true
        showStandardStreams = true
//        finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    }
}
tasks.jacocoTestReport {
//    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required.set(true)
    }
}
