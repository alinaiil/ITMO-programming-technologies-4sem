plugins {
    id("java-library")
    id("org.springframework.boot") version "3.0.5" apply false
}

apply(plugin = "io.spring.dependency-management")

group = "kz.alinaiil"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("org.springframework.boot:spring-boot-starter:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.5")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}