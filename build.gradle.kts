import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.21"
    id("maven-publish") // for publishing
}

var projectGroupId = "kr.lenope1214"
var projectVersion = "0.1.3"

group = projectGroupId
version = projectVersion
repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = projectGroupId
            artifactId = "kexcel-download"
            version = projectVersion

            from(components["kotlin"])
        }
    }
}


dependencies {
    val poiVersion = "5.2.5"
    implementation("org.apache.poi:poi:${poiVersion}")
    implementation("org.apache.poi:poi-ooxml:${poiVersion}")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testImplementation(group= "org.assertj", name= "assertj-core", version= "3.6.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.test {
    useJUnitPlatform()
}

// jvm version
tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

kotlin {
    jvmToolchain(11)
}
