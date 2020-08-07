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
    val releaseDate: Long?
)

sealed class Card {
    abstract val weight: Float
}

data class RootCard(
    val cards: List<Card>,
    override val weight: Float = 1f
) : Card() {
    constructor(vararg cards: Card) : this(cards.toList())
}

data class HorizontalGroupCard(
    val cards: List<Card>,
    override val weight: Float = 1f
) : Card() {
    constructor(vararg cards: Card) : this(cards.toList())
}

data class VerticalGroupCard(
    val cards: List<Card>,
    override val weight: Float = 1f
) : Card() {
    constructor(vararg cards: Card, weight: Float = 1f) : this(cards.toList(), weight)
}

enum class MovieImagePosition {
    LEFT, TOP, NONE
}

data class MovieCard(
    val movie: Movie,
    val imagePosition: MovieImagePosition = MovieImagePosition.NONE,
    val showPlot: Boolean = true,
    override val weight: Float = 1f
) : Card()

fun List<Movie>.toCards(): Card {
    return RootCard(
        HorizontalGroupCard(
            subList(0, 3).map { MovieCard(it, imagePosition = MovieImagePosition.LEFT, showPlot = false) }),
        HorizontalGroupCard(
            VerticalGroupCard(
                HorizontalGroupCard(
                    VerticalGroupCard(
                        subList(3, 5).map { MovieCard(it) }
                    ),
                    MovieCard(this[6], weight = 2f, imagePosition = MovieImagePosition.TOP)
                ),
                MovieCard(this[4]),
                HorizontalGroupCard(
                    MovieCard(this[6]),
                    VerticalGroupCard(
                        MovieCard(this[6]),
                        MovieCard(this[7], showPlot = false)
                    )
                ),
                MovieCard(this[9]),
                weight = 4f
            ),
            VerticalGroupCard(subList(10, 13).map { MovieCard(it) })
        ),
        VerticalGroupCard(subList(13, size).map { MovieCard(it, imagePosition = MovieImagePosition.TOP) })
    )
}
