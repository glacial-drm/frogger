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
    testImplementation("org.junit.vintage:junit-vintage-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("com.pholser:junit-quickcheck-core:1.0")
    testImplementation("com.pholser:junit-quickcheck-generators:1.0")

    //testImplementation("org.assertj:assertj-core")

    testImplementation("org.testfx:testfx-junit:4.0+")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")


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