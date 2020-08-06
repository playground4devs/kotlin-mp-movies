import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-android-extensions")
    id("com.apollographql.apollo").version("2.2.3")
}
group = "com.github.playground4devs.kmpmovies"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}
kotlin {
    android()

    val buildForDevice = project.findProperty("device") as? Boolean ?: false
    val iosTarget = if (buildForDevice) iosArm64("ios") else iosX64("ios")
    iosTarget.binaries {
        framework {
            // Disable bitcode embedding for the simulator build.
            if (!buildForDevice) {
                embedBitcode("disable")
            }
            freeCompilerArgs = freeCompilerArgs + "-Xobjc-generics"
            baseName = "shared"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.8")
                implementation("com.apollographql.apollo:apollo-api:2.2.3")
                implementation("com.apollographql.apollo:apollo-runtime-kotlin:2.2.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:1.3.1")
                implementation("com.apollographql.apollo:apollo-api:2.2.3")
            }
        }
        val androidTest by getting
        val iosMain by getting {
            dependencies {
                implementation("com.apollographql.apollo:apollo-api:2.2.3")
//                implementation("com.apollographql.apollo:apollo-runtime-kotlin-ios-x64:2.2.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.8")
            }
        }
        val iosTest by getting
    }
}
android {
    compileSdkVersion(29)
    defaultConfig {
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
}
val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.register("copyFramework", Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
            .getByName<KotlinNativeTarget>("ios")
            .binaries.getFramework(mode)

    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@ --no-configure-on-demand\n")
        gradlew.setExecutable(true)
    }
}
