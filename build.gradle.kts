plugins {
    java
    application
    id("org.jetbrains.kotlin.jvm") version "2.1.20"
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "com.hyunbindev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.12.1"


tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("com.hyunbindev.minimo")
    mainClass.set("com.hyunbindev.minimo.Launcher.kt")
}
kotlin {
    jvmToolchain(21)
}

javafx {
    version = "21.0.6"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web", "javafx.swing")
}

dependencies {
    //controls fx
    implementation("org.controlsfx:controlsfx:11.2.1")

    //sql lite driver
    implementation("org.xerial:sqlite-jdbc:3.45.1.0")

    // 1. Exposed Core (DSL & DAO 지원)
    implementation("org.jetbrains.exposed:exposed-core:0.59.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.59.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.59.0")

    // 2. 날짜/시간 처리를 위한 Java 8 Time 확장 (LocalDateTime용)
    implementation("org.jetbrains.exposed:exposed-java-time:0.59.0")

    implementation("eu.hansolo:tilesfx:21.0.9") {
        exclude(group = "org.openjfx")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}
