plugins {
    java
    `maven-publish`
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://repo.tikite.ch/releases")
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.aikar.co/content/groups/aikar/")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

dependencies {
    implementation("net.kyori:adventure-api:4.20.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.20.0")
    implementation("net.kyori:adventure-text-serializer-plain:4.20.0")
    implementation("com.typesafe:config:1.4.2")
    implementation("co.aikar:acf-core:0.5.1-SNAPSHOT")
    //implementation("net.bytebuddy:byte-buddy:1.17.5")
    compileOnly("me.combimagnetron:Passport:1.0-SNAPSHOT")
    compileOnly("com.google.guava:guava:31.1-jre")
    compileOnly("org.apache.commons:commons-lang3:3.17.0")
    compileOnly("commons-io:commons-io:2.18.0")
    compileOnly("com.github.ben-manes.caffeine:caffeine:3.2.0")
    compileOnly("com.github.retrooper:packetevents-spigot:2.9.1")
}

publishing {
    repositories {
        maven {
            name = "combimagnetron"
            url = uri("http://repo.tikite.ch/releases/")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
            isAllowInsecureProtocol = true
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "Sunscreen"
            version = project.version.toString()
            from(components["java"])
        }
    }
}