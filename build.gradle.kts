plugins {
    application
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "uk.ac.nott.cs.comp2013"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "uk.ac.nott.cs.comp2013.frogger.Main"
}

javafx {
    version = "22.0.1"
    modules = listOf("javafx.graphics", "javafx.fxml", "javafx.web", "javafx.media")
}