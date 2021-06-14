plugins {
    androidLibrary()
    kotlinAndroid()
    kotlinParcelize()
    kotlinKapt()
    daggerHilt()
}

android {
    setAndroidExtention(rootProject.rootDir.absolutePath)

    defaultConfig {
        externalNativeBuild {
            cmake {
                cppFlags("")
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.10.2"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    kotlin()
    hilt()
    room()
    retrofit()
    stetho()
    okHttp()
    gson()
    rxjava3()
    paging3()
}