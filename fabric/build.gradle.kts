plugins {
    id("com.iamkaf.multiloader.fabric")
}

extensions.configure<com.iamkaf.multiloader.fabric.MultiloaderFabricExtension>("multiloaderFabric") {
    commonDatagen.set(true)
}

dependencies {
    if (name == "26.2") {
        // LambDynamicLights
        runtimeOnly("maven.modrinth:yBW8D80W:4.12.2+26.2")
    }
}

subprojects {
    dependencies {
        if (name == "26.2") {
            // LambDynamicLights
            runtimeOnly("maven.modrinth:yBW8D80W:4.12.2+26.2")
        }
    }
}
