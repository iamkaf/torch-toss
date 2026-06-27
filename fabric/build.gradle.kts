import org.gradle.api.tasks.Sync
import org.gradle.language.jvm.tasks.ProcessResources
import java.util.Properties

plugins {
    id("com.iamkaf.multiloader.fabric")
}

val versionProperties = Properties()
val versionPropertiesFile = rootProject.file("versions/${project.name}/gradle.properties")
if (versionPropertiesFile.isFile) {
    versionPropertiesFile.inputStream().use(versionProperties::load)
}

val minecraftVersion = versionProperties.getProperty("project.minecraft")
    ?: throw GradleException("Missing project.minecraft for ${project.path}")

val useLegacyPre116FabricCompat = minecraftVersion in setOf("1.14.4", "1.15", "1.15.1", "1.15.2")
val legacySoulTorchExcludes = listOf(
    "assets/torchtoss/models/item/throwable_soul_torch.json",
    "data/torchtoss/recipes/*soul_torch*.json",
    "data/torchtoss/advancements/throw_soul_torch.json",
    "data/torchtoss/advancements/recipes/decorations/*soul_torch*.json",
)

// Torch Toss side lane: soul torches do not exist before 1.16, so resources
// generated for that feature stay excluded from the 1.14/1.15 runtime jars.
tasks.named<Sync>("stageMergedResources") {
    if (useLegacyPre116FabricCompat) {
        legacySoulTorchExcludes.forEach(::exclude)
    }
}

tasks.named<ProcessResources>("processResources") {
    if (useLegacyPre116FabricCompat) {
        legacySoulTorchExcludes.forEach(::exclude)
    }
}

extensions.configure<com.iamkaf.multiloader.fabric.MultiloaderFabricExtension>("multiloaderFabric") {
    commonDatagen.set(true)
}
