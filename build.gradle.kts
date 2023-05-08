repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

plugins {
    id("org.sonarqube") version("3.5.0.2730")
    application
}

application {
    mainClass.set("Main")
}

sourceSets {
    main {
        java {
            srcDir("src")
        }
    }
}


sonarqube {
    properties {
        property("sonar.projectKey", "iet-homework_iet-homework-not-meta")
        property("sonar.organization", "iet-homework")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}