plugins {
    id("com.iamkaf.multiloader.common")
}

// Torch Toss side lane: copper-backed generated resources only exist on the newer
// item-definition era and are not a general multiloader convention.
multiloaderCommon {
    resourcesFrom("src/copper/generated") {
        minecraftAtLeast("1.21.10")
    }
}
