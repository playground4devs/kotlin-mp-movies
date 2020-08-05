package com.github.playground4devs.kmpmovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.layout.RowScope.gravity
import androidx.ui.material.ListItem
import androidx.ui.material.Snackbar
import androidx.ui.tooling.preview.Preview
import com.github.playground4devs.kmpmovies.ui.MainScreen
import com.github.playground4devs.movies.ModelSamples
import com.github.playground4devs.movies.Movie
import com.github.playground4devs.movies.MoviesRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentMovie =
            state<Movie?>(init = { null }, areEquivalent = { old, new -> old == new })

            // TODO improved in dev15
            val movieList =
                state(init = { emptyList<Movie>() }, areEquivalent = { old, new -> old == new })


            lifecycleScope.launchWhenCreated {
                val repos = MoviesRepository().loadMovies()
                movieList.value = repos
            }
            MainScreen("Popular Movies & Series") { innerPadding ->
                LazyColumnItems(movieList.value) { movie ->
                    MovieItem(movie, onClick = { currentMovie.value = movie })
                }
            }
            currentMovie.value?.let { movie ->
                Snackbar({ Text(movie.title) })
            }
        }
    }
}


@Composable
fun MovieItem(movie: Movie, onClick: (() -> Unit)? = null) = ListItem(
    text = { Text(movie.title) },
    overlineText = { Text(movie.genres.joinToString()) },
    secondaryText = { Text(movie.plot) },
    trailing = { Text("⭐️ ${movie.rating ?: "N/A"}") },
    onClick = onClick
)

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() = MovieItem(ModelSamples.movies.first())

@Preview(showBackground = true)
@Composable
fun MovieListPreview() = LazyColumnItems(ModelSamples.movies) { movie ->
    MovieItem(movie)
}




