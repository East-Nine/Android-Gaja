plugins {
    androidLibrary()
    kotlinAndroid()
    kotlinParcelize()
    kotlinKapt()
    daggerHilt()
}

android {
    setAndroidExtention(rootProject.rootDir.absolutePath)
}

dependencies {
    kotlin()
    hilt()
    rxjava3()
    paging3()
}