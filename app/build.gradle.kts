plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    id ("androidx.navigation.safeargs")
    id("kotlin-parcelize")

}

android {
    namespace = "com.example.fragmentsbonus"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fragmentsbonus"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation (libs.navigation.fragment)
    implementation (libs.navigation.ui)
    implementation(platform(libs.firebase.bom))
    implementation(libs.swiperefreshlayout)
    implementation(libs.room.rxjava3)
    implementation (libs.rxjava3.retrofit.adapter)
    implementation (libs.rxandroid)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.retrofit)
    implementation(libs.glide)
    implementation (libs.viewpager2)
    implementation(libs.recyclerview)
    implementation(libs.protolite.well.known.types)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.converter.gson)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)






}