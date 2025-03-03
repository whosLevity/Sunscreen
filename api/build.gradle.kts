plugins {
    java
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

repositories {
    maven("http://repo.combimagnetron.xyz/releases/") {
        isAllowInsecureProtocol = true
    }
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven { url = uri("https://repo.codemc.io/repository/maven-releases/") }

    maven { url = uri("https://repo.codemc.io/repository/maven-snapshots/") }
}

dependencies {
    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.14.0")
    implementation("io.github.jglrxavpok.hephaistos:common:2.6.0")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.typesafe:config:1.4.2")
    implementation("me.combimagnetron:Passport:1.0-SNAPSHOT")
    implementation("net.sf.cssbox:cssbox:5.0.2")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("commons-io:commons-io:2.18.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.2.0")
    implementation("com.github.hkirk:java-html2image:0.9")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = "1.7.22")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8", version = "1.7.22")
    compileOnly("com.github.retrooper:packetevents-spigot:2.7.0")
}