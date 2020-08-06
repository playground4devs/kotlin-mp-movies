package com.github.playground4devs.movies

import kotlinx.serialization.Serializable

@Serializable
data class Image(
        val url: String,
        val width: Int,
        val height: Int,
        val caption: String
)

@Serializable
data class Movie(
        val title: String,
        val plot: String,
        val image: Image?,
        val rating: Double?,
        val genres: List<String>,
//        val releaseDate: TODO
)