package com.github.playground4devs.kmpmovies

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.material.IconButton
import androidx.ui.material.ListItem
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowBack
import androidx.ui.tooling.preview.Preview
import androidx.ui.viewmodel.viewModel
import com.github.playground4devs.kmpmovies.ui.KmpMovieTheme
import com.github.playground4devs.kmpmovies.ui.MainScreen
import com.github.playground4devs.movies.ModelSamples
import com.github.playground4devs.movies.Movie
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val model = viewModel<MainViewModel>()
            val currentMovieFlow = model.currentMovie

            lifecycleScope.launchWhenCreated {
                val onBackPressedCallback = onBackPressedDispatcher.addCallback(this@MainActivity) {
                    model.clearMovie()
                }
                currentMovieFlow.collect {
                    onBackPressedCallback.isEnabled = it != null
                }
            }

            val currentMovie = currentMovieFlow.collectAsState().value

            // Navigation v0.1!
            when {
                currentMovie != null -> MovieDetailScreen(
                    movie = currentMovie,
                    onArrowBackClick = { model.clearMovie() }
                )
                else -> MovieListScreen(
                    movieList = model.movieList.collectAsState().value,
                    onClickMovie = { model.onClickMovie(it) }
                )
            }
        }
    }

}

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
fun MovieDetailScreen(movie: Movie, onArrowBackClick: () -> Unit = {}) =
    KmpMovieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(movie.title) },
                    navigationIcon = {
                        IconButton(onClick = onArrowBackClick) {
                            Icon(Icons.Filled.ArrowBack)
                        }
                    }
                )
            },
            bodyContent = {
                MovieItem(movie)
            }
        )
    }

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() = MovieDetailScreen(ModelSamples.movies.first())