rootProject.name = "fukkit"

pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()

        maven {
            name = "FukkitMC"
            url = uri("../fukkit-repo")
        }

        maven {
            name = "FabricMC"
            url = uri("https://maven.fabricmc.net/")
        }
    }
}
