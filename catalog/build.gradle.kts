val dagger_version: String by project
val kotlin_version: String by project

plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode(1)
        versionName("1.0")

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    compileOptions {
        sourceCompatibility(1.8)
        targetCompatibility(1.8)
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }
}

dependencies {
    implementation(project(":app"))
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    kapt("com.google.dagger:dagger-compiler:$dagger_version")
}
