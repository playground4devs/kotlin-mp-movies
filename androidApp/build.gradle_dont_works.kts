plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}
group = "com.github.playground4devs.kmpmovies"
version = "1.0-SNAPSHOT"

val compose_version = "0.1.0-dev13"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}
dependencies {
    implementation(project(":shared"))
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.ui:ui-layout:$compose_version")
    implementation("androidx.ui:ui-material:$compose_version")
    implementation("androidx.ui:ui-tooling:$compose_version")
}
android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.github.playground4devs.androidApp"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
        kotlinCompilerVersion = "1.3.70-dev-withExperimentalGoogleExtensions-20200424"
    }
}