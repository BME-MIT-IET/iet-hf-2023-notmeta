repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

plugins {
    id("org.sonarqube") version("4.0.0.2929")
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

tasks.withType<Jar>{
    manifest {
        attributes["Main-Class"] = "Main"
    }
}

dependencies {
    implementation("junit:junit:4.13.1")
    implementation("io.cucumber:cucumber-java:6.10.4")
    implementation("io.cucumber:cucumber-junit:6.10.4")
    implementation("org.junit.vintage:junit-vintage-engine:5.7.2")
}