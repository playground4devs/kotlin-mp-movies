package com.github.playground4devs.kmpmovies

import androidx.compose.Composable
import androidx.ui.foundation.Text
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.material.ListItem
import androidx.ui.tooling.preview.Preview
import com.github.playground4devs.kmpmovies.ui.MainScreen
import com.github.playground4devs.movies.Lce
import com.github.playground4devs.movies.ModelSamples
import com.github.playground4devs.movies.Movie

@Composable
fun MovieListScreen(movieList: Lce<List<Movie>>, onClickMovie: (Movie) -> Unit = {}) =
    MainScreen("Popular Movies & Series") { innerPadding ->
        when (movieList) {
            is Lce.Success -> LazyColumnItems(movieList.data) { movie ->
                MovieItem(movie, onClickMovie)
            }
            is Lce.Loading -> {
                Text("Loading...")
            }
            is Lce.Error -> {
                Text("Error...")
            }
        }
    }

@Preview(showBackground = true)
@Composable
fun MovieListScreenPreview() = MovieListScreen(Lce.Success(ModelSamples.movies))

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
