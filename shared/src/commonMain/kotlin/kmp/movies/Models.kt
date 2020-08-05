package kmp.movies

data class Image(
        val url: String,
        val width: Int,
        val height: Int,
        val caption: String
)

data class Movie(
        val title: String,
        val plot: String,
        val image: Image?,
        val rating: Double?,
        val genres: List<String>,
//        val releaseDate: TODO
)