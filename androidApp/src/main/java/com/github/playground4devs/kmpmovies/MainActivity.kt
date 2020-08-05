package com.github.playground4devs.kmpmovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.preferredHeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.github.playground4devs.movies.ModelSamples
import com.github.playground4devs.kmpmovies.ui.MainScreen
import com.github.playground4devs.movies.Movie
import com.github.playground4devs.movies.MoviesRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO improved in dev15
            val movieList =
                state(init = { emptyList<Movie>() }, areEquivalent = { old, new -> old == new })

            lifecycleScope.launchWhenCreated {
                val repos = MoviesRepository().loadMovies()
                movieList.value = repos
            }
            MainScreen("Movies") { innerPadding ->
                VerticalScroller {
                    movieList.value.forEach { movie ->
                        MovieItem(movie)
                    }
                }
            }
        }
    }
}


@Composable
fun MovieItem(movie: Movie) {
    Text(
        text = movie.title,
        modifier = Modifier.fillMaxWidth().preferredHeight(50.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(ModelSamples.movies.first())
}

@Preview(showBackground = true)
@Composable
fun MovieListPreview() {
    VerticalScroller {
        ModelSamples.movies.forEach {
            MovieItem(it)
        }
    }
}




