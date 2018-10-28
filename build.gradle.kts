plugins {
    java
}

repositories {
    jcenter()
}

group = "de.ferdinandhofherr"
version = project.properties["project.version"]!!

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val junitVersion = "5.3.1"

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    implementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
