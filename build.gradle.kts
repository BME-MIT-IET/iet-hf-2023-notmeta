repositories {
    mavenCentral()
}

plugins {
    application
    id("org.sonarqube") version("4.0.0.2929")
}

tasks.test {
    useJUnitPlatform()
    filter {
        includeTestsMatching("**cucumber**")
    }
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
    test {
        java {
            srcDir("src/test")
            exclude("**/ui/**") // Adjusted exclusion pattern
        }
        resources {
            srcDir("src/test/resources")
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

tasks.create<Test>("Run UI Test") {
    filter {
        includeTestsMatching("ui.*")
    }
}

dependencies {
    implementation("junit:junit:4.13.1")
    implementation("io.cucumber:cucumber-java:6.10.4")
    implementation("io.cucumber:cucumber-junit:6.10.4")
    implementation("org.junit.vintage:junit-vintage-engine:5.7.2")
    implementation("org.assertj:assertj-swing:3.17.0")
}
