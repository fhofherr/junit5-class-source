import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    `java-library`
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
}

repositories {
    mavenCentral()
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

    testImplementation("org.mockito:mockito-core:2.23.0")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

val javadoc by tasks.existing(Javadoc::class) {
    isFailOnError = false
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(javadoc)

    classifier = "javadoc"
    from(sourceSets["main"].allSource)
}

val junit5ClassSourcePublication = "junit5ClassSource"
publishing.publications {
    register(junit5ClassSourcePublication, MavenPublication::class) {
        from(components["java"])
        artifact(sourcesJar.get())
        artifact(javadocJar.get())

        groupId = project.group as String
        artifactId = rootProject.name
        version = project.version as String

        pom.withXml {
            asNode().let { rootNode ->
                rootNode.appendNode("description", "@ClassSource annotation for Juni5 parameterized tests")
                rootNode.appendNode("name", rootProject.name)
                rootNode.appendNode("url", "https://github.com/fhofherr/junit5-class-source")

                rootNode.appendNode("licenses").let { licensesNode ->
                    licensesNode.appendNode("license").let {
                        it.appendNode("name", "MIT")
                        it.appendNode("url", "https://opensource.org/licenses/MIT")
                        it.appendNode("distribution", "repo")
                    }
                }

                rootNode.appendNode("developers").let { developersNode ->
                    developersNode.appendNode("developer").let {
                        it.appendNode("id", "fhofherr")
                        it.appendNode("name", "Ferdinand Hofherr")
                        it.appendNode("email", "mail@ferdinandhofherr.de")
                    }
                }

                rootNode.appendNode("scm").let { scmNode ->
                    scmNode.appendNode("url", "https://github.com/fhofherr/junit5-class-source")
                }
            }
        }
    }
}


bintray {
    user = System.getProperty("bintray.user")
    key = System.getProperty("bintray.key")
    setPublications(junit5ClassSourcePublication)

    // Workaround for Kotlin DSL. The pkg {} closure does not seem to work.
    pkg.repo = "oss"
    pkg.name = rootProject.name
    pkg.userOrg = "fhofherr"
    pkg.websiteUrl = "https://github.com/fhofherr/junit5-class-source"
    pkg.vcsUrl = "https://github.com/fhofherr/junit5-class-source"
    pkg.setLabels("junit5", "java", "testing", "parameterized-tests")
    pkg.setLicenses("MIT")
}
