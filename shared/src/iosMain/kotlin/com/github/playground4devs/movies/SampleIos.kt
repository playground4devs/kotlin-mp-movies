package com.github.playground4devs.movies

actual class Sample {
    actual fun checkMe() = 7
}

actual object Platform {
    actual val name: String = "iOS"
}

fun doCoroutine() = SuspendWrapper { awaitTest() }

fun getMoviesBlocking(): List<Movie> =
        SuspendWrapper { MovieRepository().loadMoviesForIOS() }
                .blockingGet<List<Movie>>()
                .toList()

fun getMovies() : SuspendWrapper<List<Movie>> = SuspendWrapper {
    println("Downloading movies")
    MovieRepository().loadMoviesForIOS()
}