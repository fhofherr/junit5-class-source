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
