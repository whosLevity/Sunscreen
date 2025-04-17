plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    //id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

group = "me.combimagnetron"
version = "0.1.0"

configurations {
    create("excludeShadowJar") {
        extendsFrom(project(":api").configurations["compileOnly"])
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://repo.combimagnetron.xyz/releases/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven { url = uri("https://repo.codemc.io/repository/maven-releases/") }

    maven { url = uri("https://repo.codemc.io/repository/maven-snapshots/") }
    maven {
        url = uri("https://repo.extendedclip.com/releases/")
    }
}

private val configuration : String = "reobf"

configurations.all() {
    resolutionStrategy {
        cacheChangingModulesFor(0, "seconds")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    runServer {
        minecraftVersion("1.21.4")
        jvmArgs("-Dcom.mojang.eula.agree=true", "-Dfile.encoding=UTF-8", "--nogui")
        downloadPlugins {
            github("retrooper", "packetevents", "v2.7.0", "packetevents-spigot-2.7.0.jar")
            hangar("PlaceholderAPI", "2.11.6")
        }
    }

    build {
        dependsOn( "shadowJar")
    }

    shadowJar {
        archiveBaseName.set("Sunscreen")
        archiveVersion.set(this.project.version.toString())
        configurations = listOf(project.configurations.runtimeClasspath.get())
        dependencies {
            exclude(dependency("com.google.guava:guava:31.1-jre"))
            exclude(dependency("org.apache.commons:commons-lang3:3.17.0"))
            exclude(dependency("commons-io:commons-io:2.18.0"))
            exclude(dependency("com.github.ben-manes.caffeine:caffeine:3.2.0"))
            exclude(dependency("org.jetbrains.kotlin:kotlin-reflect:1.7.22"))
            exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.22"))
        }
    }
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

dependencies {
    implementation(project(":api"))
    implementation("me.combimagnetron:Passport:1.0-SNAPSHOT")
    implementation("io.github.revxrsal:lamp.common:4.0.0-rc.10")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-rc.10")
    implementation("io.github.revxrsal:lamp.brigadier:4.0.0-rc.10")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("net.kyori:adventure-api:4.14.0")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.14.0")
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("com.github.retrooper:packetevents-spigot:2.7.0")
}

bukkit {
    name = "Sunscreen"
    main = "me.combimagnetron.sunscreen.SunscreenPlugin"
    apiVersion = "1.20"
    version = project.version.toString()
    authors = listOf("Combimagnetron")
    description = "Create UIs like never seen before, all from within the game!"
    libraries = listOf("com.google.guava:guava:31.1-jre",
        "org.apache.commons:commons-lang3:3.17.0",
        "org.jetbrains.kotlin:kotlin-reflect:1.7.22",
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.22",
        "commons-io:commons-io:2.18.0",
        "com.github.ben-manes.caffeine:caffeine:3.2.0",
        "org.apache.commons:commons-lang3:3.17.0"
        )
    website = "https://combimagnetron.me"

    dependencies {
        depend = listOf("packetevents")
    }
}