import com.iamkaf.multiloader.support.MultiloaderProjectContext

plugins {
    id("com.iamkaf.multiloader.common")
}

val multiloader = MultiloaderProjectContext.of(project)

dependencies {
    api(multiloader.library(multiloader.catalogFor(), "konfig"))
}
