plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("de.eldoria.plugin-yml.bukkit") version "0.7.1"
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
    maven("https://mvn.lumine.io/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://repo.tikite.ch/releases")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
    maven("https://repo.extendedclip.com/releases/")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://jitpack.io")
    maven("https://repo.nexomc.com/releases")
}

configurations.all {
    resolutionStrategy {
        cacheChangingModulesFor(0, "seconds")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    runServer {
        minecraftVersion("1.21.7")
        jvmArgs("-Dcom.mojang.eula.agree=true", "-Dfile.encoding=UTF-8", "--nogui")
        downloadPlugins {
            github("retrooper", "packetevents", "v2.9.1", "packetevents-spigot-2.9.1.jar")
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

val betterHudVersion = "1.12.2"
val adventureVersion = "4.20.0"

dependencies {
    implementation(project(":api"))
    implementation("me.combimagnetron:Passport:1.0-SNAPSHOT")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
    library("commons-io:commons-io:2.18.0")
    library("com.google.guava:guava:31.1-jre")
    library("org.apache.commons:commons-lang3:3.17.0")
    library("org.jetbrains.kotlin:kotlin-reflect:1.7.22")
    library("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.22")
    library("commons-io:commons-io:2.18.0")
    library("com.github.ben-manes.caffeine:caffeine:3.2.0")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("net.kyori:adventure-api:${adventureVersion}")
    compileOnly("net.kyori:adventure-text-serializer-gson:${adventureVersion}")
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("com.github.retrooper:packetevents-spigot:2.9.1")
    //compileOnly("io.lumine.mythichud:api:1.2.4-SNAPSHOT")
    compileOnly("io.github.toxicity188:BetterHud-standard-api:${betterHudVersion}")
    compileOnly("io.github.toxicity188:BetterHud-bukkit-api:${betterHudVersion}")
    compileOnly("io.github.toxicity188:BetterCommand:1.4.3")
    compileOnly("com.github.NEZNAMY:TAB-API:5.2.0")
    compileOnly("com.nexomc:nexo:1.5.0")
    compileOnly("team.unnamed:creative-api:1.7.3")
    compileOnly("team.unnamed:creative-serializer-minecraft:1.7.3")
}

bukkit {
    name = "Sunscreen"
    main = "me.combimagnetron.sunscreen.SunscreenPlugin"
    apiVersion = "1.20"
    version = project.version.toString()
    authors = listOf("Combimagnetron")
    description = "Create UIs like never seen before, all from within the game!"
    website = "https://combimagnetron.me"
    dependencies {
        depend = listOf("packetevents")
        softDepend = listOf("MythicHUD", "BetterHud", "TAB")
    }
}