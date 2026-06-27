import org.gradle.api.tasks.SourceSetContainer
import java.util.Properties

plugins {
    id("com.iamkaf.multiloader.common")
}

val versionProperties = Properties()
val versionPropertiesFile = rootProject.file("versions/${project.name}/gradle.properties")
if (versionPropertiesFile.isFile) {
    versionPropertiesFile.inputStream().use(versionProperties::load)
}

val minecraftVersion = versionProperties.getProperty("project.minecraft")
    ?: throw GradleException("Missing project.minecraft for ${project.path}")

fun versionAtLeast(current: String, target: String): Boolean {
    fun parse(version: String): List<Int> =
        version.split(".").map { part ->
            Regex("\\d+").find(part)?.value?.toInt() ?: 0
        }

    val lhs = parse(current)
    val rhs = parse(target)
    val size = maxOf(lhs.size, rhs.size)
    for (index in 0 until size) {
        val left = lhs.getOrElse(index) { 0 }
        val right = rhs.getOrElse(index) { 0 }
        if (left != right) return left > right
    }
    return true
}

// Torch Toss side lane: copper-backed generated resources only exist on the newer
// item-definition era and are not a general multiloader convention.
extensions.configure<SourceSetContainer>("sourceSets") {
    named("main") {
        if (versionAtLeast(minecraftVersion, "1.21.10")) {
            resources.srcDir(file("src/copper/generated"))
        }
    }
}
