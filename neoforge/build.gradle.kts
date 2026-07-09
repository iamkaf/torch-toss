plugins {
    id("com.iamkaf.multiloader.neoforge")
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
