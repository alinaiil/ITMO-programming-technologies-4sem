plugins {
    id("java")
}

group = "kz.alinaiil"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":kotiki:data")))
    implementation(project(mapOf("path" to ":kotiki:data")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("org.hibernate:hibernate-core:6.1.7.Final")
    testImplementation("com.h2database:h2:1.3.148")

}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}