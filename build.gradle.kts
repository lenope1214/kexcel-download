plugins {
    kotlin("jvm") version "1.9.21"
}

group = "kr.lenope1214"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
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
kotlin {
    jvmToolchain(17)
}
