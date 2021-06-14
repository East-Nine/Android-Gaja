// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Hilt.version}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = Apps.jvmVersion
        kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
}

tasks.register("clean",Delete::class) {
    delete(rootProject.buildDir)
}