plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.mohmmed.mosa.eg.towmmen"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mohmmed.mosa.eg.towmmen"
        minSdk = 22
        targetSdk = 34
        versionCode = 5
        versionName = "1.2.5-beta"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
                arguments["room.incremental"] = "true"
                arguments["room.expandProjection"] = "true"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
kapt {
    correctErrorTypes = true
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.hilt.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.leakcanary.android)

    // for desugring
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    //Splash Api
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.barcode.scanning)
    //calendar view
    implementation(libs.caleandra.view)
    // requsr permision
    implementation(libs.accompanist.permissions)

    // work manger
    implementation(libs.androidx.work.runtime.ktx)
    //implementation (libs.androidx.work.multiprocess)
    implementation(libs.androidx.hilt.work)
    // coil
    implementation(libs.coil.compose)
    // dagger
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)
    // data store
    implementation(libs.androidx.datastore.preferences)
    // Compose Foundation
    implementation(libs.androidx.foundation)
    // Accompanist
    implementation(libs.accompanist.systemuicontroller)
    // Paging 3
    //implementation (libs.androidx.paging.runtime)
    //implementation (libs.androidx.paging.compose)
    //Room
    implementation (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    implementation( libs.androidx.room.ktx)
    // window size class
    implementation(libs.androidx.material3.window.size)
    // navigation-compose
    implementation(libs.androidx.navigation.compose)
    // pretty time
    implementation(libs.prettytime)
    // to seleporite
    implementation(libs.dionsegijn.konfetti)
    // chart vice
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.core)
}