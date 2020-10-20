package com.github.playground4devs.movies

import com.russhwolf.settings.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class MoviePersister {

    private val json = Json { allowStructuredMapKeys = true }

    private val settings: Settings = Settings()

    private var cache by settings.nullableString(key = "cache")

    fun persist(moviesFromServer: List<Movie>) {
        cache = json.encodeToString(ListSerializer(Movie.serializer()), moviesFromServer)
    }

    fun load(): List<Movie>? =
        cache?.let { json.decodeFromString(ListSerializer(Movie.serializer()), it) }
}