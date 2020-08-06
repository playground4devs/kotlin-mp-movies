package com.github.playground4devs.movies

import com.russhwolf.settings.Settings
import com.russhwolf.settings.invoke
import com.russhwolf.settings.long
import com.soywiz.klock.DateTime

class MovieRepository(
    private val fetcher: MovieFetcher = MovieFetcher(),
    private val persister: MoviePersister = MoviePersister()
) {

    private val settings: Settings = Settings()

    private var lastDownload by settings.long(key = "last_download")

    suspend fun loadMovies(): List<Movie> {
        val now = DateTime.now().unixMillisLong
        return if (now - lastDownload > 1000 * 60 * 5) {
            try {
                val moviesFromServer = fetcher.fetchMovies()
                persister.persist(moviesFromServer)
                lastDownload = now
                moviesFromServer
            } catch (e: Exception) {
                persister.load() ?: throw e
            }
        } else {
            persister.load()!!
        }
    }
}
