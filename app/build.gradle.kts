plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "banquemisr.challenge05"
    compileSdk = 34

    defaultConfig {
        applicationId = "banquemisr.challenge05"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_BASE", "\"https://api.themoviedb.org/3/movie/\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true

    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // AndroidX
    implementation ("androidx.activity:activity-ktx:1.6.1")
    implementation ("androidx.appcompat:appcompat:1.6.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.fragment:fragment-ktx:1.5.5")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("androidx.vectordrawable:vectordrawable-seekable:1.0.0-beta01")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

//    runtimeOnly("org.jetbrains.kotlin:kotlin-parcelize-runtime:1.7.10-286")

    // API Consumption
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // images
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("jp.wasabeef:picasso-transformations:2.4.0")
    annotationProcessor ( "com.squareup.moshi:moshi-kotlin-codegen:1.12.0")


    // Lifecycle
    val lifecycle_version = "2.5.1"
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // RxAndroid
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")


    // App Intro
//    implementation ("com.github.AppIntro:AppIntro:6.1.0")
    // lotti animation
    implementation ("com.airbnb.android:lottie:5.2.0")
    // Dots Indicator
    implementation ("com.tbuonomo:dotsindicator:4.2")

// glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")




    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}