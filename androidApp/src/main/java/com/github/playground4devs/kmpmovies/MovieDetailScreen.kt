package com.github.playground4devs.kmpmovies

import androidx.compose.Composable
import androidx.ui.tooling.preview.Preview
import com.github.playground4devs.kmpmovies.ui.SecondaryScreen
import com.github.playground4devs.movies.ModelSamples
import com.github.playground4devs.movies.Movie

@Composable
fun MovieDetailScreen(movie: Movie, onArrowBackClick: () -> Unit = {}) =
    SecondaryScreen(movie.title, onArrowBackClick) { innerPadding ->
        MovieItem(movie)
    }

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() = MovieDetailScreen(ModelSamples.movies.first())