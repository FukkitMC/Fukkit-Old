rootProject.name = "fukkit"

pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()

        maven {
            name = "FukkitMC"
            url = uri("https://raw.githubusercontent.com/FukkitMC/fukkit-repo/master")
        }

        maven {
            name = "FabricMC"
            url = uri("https://maven.fabricmc.net/")
        }
    }
}
