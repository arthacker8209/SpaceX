plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.kotlinParcelize)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.deepak.spacex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.deepak.spacex"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    buildConfigField("String", "BASE_URL", "\"https://api.spacexdata.com/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.lifecycle.runtime.ktx)

    //dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //Networking
    implementation(libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation (libs.converter.scalars)

    implementation(libs.bundles.room)
    implementation(libs.coil.compose)
    kapt(libs.room.compiler)
    annotationProcessor(libs.room.compiler)

    implementation(libs.datastore.preferences)

    implementation(libs.androidx.fragment.ktx)

    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)
    implementation(libs.threetenabp)
    implementation(libs.androidx.viewpager2)
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

}
kapt {
    correctErrorTypes = true
}