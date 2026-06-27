plugins {
    id("com.iamkaf.multiloader.common")
}

// Torch Toss side lanes: feature-specific generated resources only join the
// versions where the corresponding vanilla torch exists.
multiloaderCommon {
    resourcesFrom("src/soul/generated") {
        minecraftAtLeast("1.16")
    }

    resourcesFrom("src/copper/generated") {
        minecraftAtLeast("1.21.10")
    }
}
