plugins {
    id("com.iamkaf.multiloader.fabric")
}

extensions.configure<com.iamkaf.multiloader.fabric.MultiloaderFabricExtension>("multiloaderFabric") {
    commonDatagen.set(true)
}

repositories {
    maven("https://maven.gegy.dev/") {
        name = "Gegy"
        content {
            includeGroup("dev.lambdaurora")
            includeGroup("dev.lambdaurora.lambdynamiclights")
            includeGroup("dev.yumi.mc.core")
            includeGroup("io.github.queerbric")
        }
    }
}

dependencies {
    if (name == "1.21.1") {
        // LambDynamicLights
        "modRuntimeOnly"("maven.modrinth:yBW8D80W:4.8.10+1.21.1")
        "modRuntimeOnly"("dev.lambdaurora.lambdynamiclights:lambdynamiclights-runtime:4.8.10+1.21.1")
    }

    if (name == "26.2") {
        // LambDynamicLights
        runtimeOnly("maven.modrinth:yBW8D80W:4.12.2+26.2")
    }
}

subprojects {
    repositories {
        maven("https://maven.gegy.dev/") {
            name = "Gegy"
            content {
                includeGroup("dev.lambdaurora")
                includeGroup("dev.lambdaurora.lambdynamiclights")
                includeGroup("dev.yumi.mc.core")
                includeGroup("io.github.queerbric")
            }
        }
    }

    dependencies {
        if (name == "1.21.1") {
            // LambDynamicLights
            "modRuntimeOnly"("maven.modrinth:yBW8D80W:4.8.10+1.21.1")
            "modRuntimeOnly"("dev.lambdaurora.lambdynamiclights:lambdynamiclights-runtime:4.8.10+1.21.1")
        }

        if (name == "26.2") {
            // LambDynamicLights
            runtimeOnly("maven.modrinth:yBW8D80W:4.12.2+26.2")
        }
    }
}
