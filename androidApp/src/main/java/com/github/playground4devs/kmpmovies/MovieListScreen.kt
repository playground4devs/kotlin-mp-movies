package com.github.playground4devs.kmpmovies

import androidx.compose.Composable
import androidx.ui.foundation.Text
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.material.ListItem
import androidx.ui.tooling.preview.Preview
import com.github.playground4devs.kmpmovies.ui.MainScreen
import com.github.playground4devs.movies.ModelSamples
import com.github.playground4devs.movies.Movie

@Composable
fun MovieListScreen(movieList: List<Movie>, onClickMovie: (Movie) -> Unit = {}) =
    MainScreen("Popular Movies & Series") { innerPadding ->
        LazyColumnItems(movieList) { movie ->
            MovieItem(movie, onClickMovie)
        }
    }

@Preview(showBackground = true)
@Composable
fun MovieListScreenPreview() = MovieListScreen(ModelSamples.movies)

@Composable
fun MovieItem(movie: Movie, onClickMovie: (Movie) -> Unit = {}) = ListItem(
    text = { Text(movie.title) },
    overlineText = { Text(movie.genres.joinToString()) },
    secondaryText = { Text(movie.plot) },
    trailing = { Text("⭐️ ${movie.rating ?: "N/A"}") },
    onClick = { onClickMovie(movie) }
)

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() = MovieItem(ModelSamples.movies.first())
