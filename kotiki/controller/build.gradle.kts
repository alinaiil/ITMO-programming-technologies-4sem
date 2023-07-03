plugins {
    id("java")
    id("org.springframework.boot") version "3.0.5"
}

apply(plugin = "io.spring.dependency-management")

group = "kz.alinaiil"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":kotiki:service")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("com.h2database:h2:1.3.148")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.5")
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-security:3.0.5")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}