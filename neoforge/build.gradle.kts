plugins {
    id("com.iamkaf.multiloader.neoforge")
}

dependencies {
    if (name == "1.21.1") {
        // LambDynamicLights
        runtimeOnly("maven.modrinth:yBW8D80W:4.8.10+1.21.1")
    }

    if (name == "26.2") {
        // LambDynamicLights
        runtimeOnly("maven.modrinth:yBW8D80W:4.12.2+26.2")
    }
}

subprojects {
    dependencies {
        if (name == "1.21.1") {
            // LambDynamicLights
            runtimeOnly("maven.modrinth:yBW8D80W:4.8.10+1.21.1")
        }

        if (name == "26.2") {
            // LambDynamicLights
            runtimeOnly("maven.modrinth:yBW8D80W:4.12.2+26.2")
        }
    }
}
