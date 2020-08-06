buildscript {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
        classpath("com.android.tools.build:gradle:4.2.0-alpha07")
        classpath(kotlin("serialization", version = "1.3.72"))
    }
}
group = "com.github.playground4devs.kmpmovies"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }
}

//repositories {
//    google()
//    mavenCentral()
//    jcenter()
//    maven {
//        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
//    }
//}
