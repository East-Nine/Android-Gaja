import com.android.build.gradle.TestedExtension
import org.gradle.api.JavaVersion
import java.io.File

fun TestedExtension.setAndroidExtention(rootDirectory: String) {
    compileSdkVersion(Apps.compileSdk)
    buildToolsVersion(Apps.buildTools)
    
    defaultConfig {
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
        versionCode = Apps.versionCode
        versionName = Apps.versionName
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    
    buildTypes {
        getByName(Build.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            consumerProguardFiles(File("$rootDirectory/buildSrc/consumerRules/consumer-rules.pro"))
        }
        getByName(Build.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            consumerProguardFiles(File("$rootDirectory/buildSrc/consumerRules/consumer-rules.pro"))
        }
    }
    
    packagingOptions {
        exclude("META-INF/LICENSE*")
    }
}