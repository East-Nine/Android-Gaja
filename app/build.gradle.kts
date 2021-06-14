import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    androidApp()
    kotlinAndroid()
    kotlinParcelize()
    kotlinKapt()
    daggerHilt()
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    setAndroidExtention(rootProject.rootDir.absolutePath)

    defaultConfig {
        applicationId = "com.eastnine.gaja"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":domain"))
    implementation(project(":data"))

    kotlin()
    androidX()
    activity()
    fragment()
    view()
    lifeCycle()
    navigation()
    hilt()
    room()
    stetho()
    retrofit()
    rxjava3()
    glide()
    paging3()

    test()
}