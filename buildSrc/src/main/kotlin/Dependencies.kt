object Apps {
    const val compileSdk = 30
    const val buildTools = "30.0.2"
    const val minSdk = 21
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val jvmVersion = "1.8"
}

object Versions {
    const val gradle = "4.1.2"
}

object Kotlin {
    const val version = "1.4.31"
    
    const val kotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    
    object Coroutine {
        private const val group = "org.jetbrains.kotlinx"
        const val android = "$group:kotlinx-coroutines-android:1.4.2"
        const val core = "$group:kotlinx-coroutines-core:1.4.2"
    }
}

object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    const val core = "androidx.core:core-ktx:1.3.2"
    
    object Activity {
        private const val version = "1.2.0-rc01"
        const val activity = "androidx.activity:activity:$version"
        const val activityKtx = "androidx.activity:activity-ktx:$version"
    }
    
    object Fragment {
        private const val version = "1.3.0-rc01"
        const val fragment = "androidx.fragment:fragment:$version"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
    }
    
    object View {
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val material = "com.google.android.material:material:1.2.1"
    }
    
    object Lifecycle {
        private const val group = "androidx.lifecycle"
        private const val version = "2.2.0"
        
        const val viewModel = "$group:lifecycle-viewmodel:$version"
        const val viewModelKtx = "$group:lifecycle-viewmodel-ktx:$version"
        const val liveData = "$group:lifecycle-livedata:$version"
        const val liveDataKtx = "$group:lifecycle-livedata-ktx:$version"
        const val lifeCycleExtension = "$group:lifecycle-extensions:$version"
        const val LifecycleScope = "$group:lifecycle-runtime-ktx:$version"
    }

    object Navigation {
        private const val group = "androidx.navigation"
        const val version = "2.3.5"

        const val navigationFragment = "$group:navigation-fragment:$version"
        const val navigationUi = "$group:navigation-ui:$version"
        const val navigationFragmentKtx = "$group:navigation-fragment-ktx:$version"
        const val navigationUiKtx = "$group:navigation-ui-ktx:$version"
        const val navigationTesting = "$group:navigation-testing:$version"
    }

    object Paging3 {
        const val version = "3.0.0"

        const val runtimeKtx = "androidx.paging:paging-runtime-ktx:$version"
        const val rxjava3 = "androidx.paging:paging-rxjava3:$version"
    }
}

object Hilt {
    private const val group = "com.google.dagger"
    const val version = "2.35"
    
    const val hiltAndroid = "$group:hilt-android:$version"
    const val hiltCompiler = "$group:hilt-android-compiler:$version"
}

object Room {
    private const val group = "androidx.room"
    private const val version = "2.3.0"

    const val runtime = "$group:room-runtime:$version"
    const val compiler = "$group:room-compiler:$version"
    const val ktx = "$group:room-ktx:$version"
    const val rxJava3 = "$group:room-rxjava3:$version"
    const val testing = "$group:room-testing:$version"
}

object Retrofit {
    private const val group = "com.squareup.retrofit2"
    private const val version = "2.9.0"
    const val retrofit = "$group:retrofit:$version"
    const val converterGson = "$group:converter-gson:$version"
    const val adapterRxJava3 = "$group:adapter-rxjava3:$version"
}

object OkHttp {
    private const val group = "com.squareup.okhttp3"
    const val logging = "$group:logging-interceptor:4.8.1"
}

object Stetho {
    private const val version = "1.6.0"
    private const val group = "com.facebook.stetho"
    const val stetho = "$group:stetho:$version"
    const val okhttp3 = "$group:stetho-okhttp3:$version"
    const val urlconnection = "$group:stetho-urlconnection:$version"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:4.11.0"
}

object Gson {
    const val gson = "com.google.code.gson:gson:2.8.6"
}

object RxJava3 {
    private const val group = "io.reactivex.rxjava3"
    const val rxjava = "$group:rxjava:3.0.13"
    const val rxandroid = "$group:rxandroid:3.0.0"
    const val rxkotlin = "$group:rxkotlin:3.0.1"
}

object Test {
    const val junit = "junit:junit:4.13"
    const val extJunit = "androidx.test.ext:junit:1.1.2"
    const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
}