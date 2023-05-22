
repositories {
    mavenCentral()
}

plugins {
    application
    id("org.sonarqube") version("4.0.0.2929")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Jar>{
    manifest {
        attributes["Main-Class"] = "Main"
    }
}

tasks.withType(JavaCompile::class) {
    options.release.set(11)
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

tasks.withType<Jar>{
    manifest {
        attributes["Main-Class"] = "Main"
    }
}