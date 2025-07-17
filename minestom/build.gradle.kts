plugins {
    id("java")
}

group = "me.combimagnetron"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://repo.tikite.ch/releases")
    maven("https://jitpack.io")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    implementation(project(":api"))
    implementation("me.combimagnetron:Passport:1.0-SNAPSHOT")
    implementation("com.github.retrooper:packetevents-api:2.8.0")
    compileOnly("net.minestom:minestom-snapshots:7320437640")
    compileOnly("commons-io:commons-io:2.18.0")
}

tasks.test {
    useJUnitPlatform()
}