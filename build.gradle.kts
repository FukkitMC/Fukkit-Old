import io.github.fukkitmc.gloom.definitions.ClassDefinition

plugins {
    id("fabric-loom") version "g0.2.6-20200215.091140-9"
    id("io.github.fukkitmc.crusty") version "1.1.5"
}

group = "io.github.fukkitmc"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

minecraft {
    loadDefinitions("definitions/access.json")
    loadDefinitions("definitions/access_extra.json")
    loadDefinitions("definitions/definitions.json")

    // Automatically add extras
    for (f in File("src/main/java/io/github/fukkitmc/fukkit/extra").list() ?: return@minecraft) {
        val file = f.substring(0, f.length - 10)
        addDefinitions(ClassDefinition("net/minecraft/server/$file", setOf("io/github/fukkitmc/fukkit/extra/${file}Extra"), setOf(), setOf(), setOf(), setOf(), setOf()))
    }
}

repositories {
    jcenter()

    maven {
        name = "SpigotMC Public"
        url = uri("https://hub.spigotmc.org/nexus/content/groups/public/")
    }
}

dependencies {
    minecraft("net.minecraft", "minecraft", "1.15.2")
    mappings(fukkit.mappings("1.15.2"))
    modCompile("net.fabricmc", "fabric-loader", "0.7.6+build.179")

    implementation("org.bukkit", "bukkit", "1.15.2-R0.1-20200210.052821-50")
    implementation("jline", "jline", "2.12.1")
    implementation("com.googlecode.json-simple", "json-simple", "1.1.1")
    compileOnly("com.google.code.findbugs", "jsr305", "3.0.2")
}
