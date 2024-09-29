import com.google.devtools.ksp.processing.kspJsArgParser

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp.android.plugin)
    alias(libs.plugins.hilt.android)

}

android {
    namespace = "com.ngengeapps.weather"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ngengeapps.weather"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)
    implementation(libs.wear.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.viewmodel.compose.runtime)
    implementation(libs.viewmodel.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.engine.okhttp)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.viewmodel.compose)
    implementation(libs.play.services)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.kamel.image)
    implementation(libs.compose.navigation)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.dagger.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.com.google.devtools.ksp)
    implementation(libs.compose.material.icons.extended)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}