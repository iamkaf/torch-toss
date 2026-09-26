import com.iamkaf.multiloader.support.MultiloaderProjectContext

plugins {
    id("com.iamkaf.multiloader.forge")
}

dependencies {
    val multiloader = MultiloaderProjectContext.of(project)
    compileOnly(multiloader.library(multiloader.catalogFor(), "konfig"))
}
