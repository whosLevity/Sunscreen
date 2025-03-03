plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.combimagnetron"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("http://78.47.189.94:80/releases/") {
        isAllowInsecureProtocol = true
    }
    maven("https://repo.papermc.io/repository/maven-public/")
    maven { url = uri("https://repo.codemc.io/repository/maven-releases/") }

    maven { url = uri("https://repo.codemc.io/repository/maven-snapshots/") }
}

private val configuration : String = "reobf"

configurations.all() {
    resolutionStrategy {
        cacheChangingModulesFor(0, "seconds")
    }
}

dependencies {
    implementation(project(":api"))
    //implementation(project(":spigot-v1_20_1", configuration = configuration))
    implementation("me.combimagnetron:Passport:1.0-SNAPSHOT")
    implementation("net.kyori:adventure-api:4.14.0")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.14.0")
    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly("com.github.retrooper:packetevents-spigot:2.7.0")
}

tasks {
    build {
        dependsOn( "shadowJar")
    }
}

bukkit {
    name = "Sunscreen"
    main = "me.combimagnetron.sunscreen.SunscreenPlugin"
    apiVersion = "1.20"
    dependencies {
        depend = listOf("packetevents")
    }
}