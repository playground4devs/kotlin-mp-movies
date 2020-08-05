package com.github.playground4devs.movies

actual class Sample {
    actual fun checkMe() = 7
}

actual object Platform {
    actual val name: String = "iOS"
}

fun doCoroutine() = SuspendWrapper { awaitTest() }