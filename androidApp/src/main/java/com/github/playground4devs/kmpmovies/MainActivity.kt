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
import com.github.playground4devs.kmpmovies.ui.SecondaryScreen
import com.github.playground4devs.movies.Lce
import com.github.playground4devs.movies.ModelSamples
import com.github.playground4devs.movies.Movie
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val model = viewModel<MainViewModel>()
            val currentMovieFlow = model.currentMovie


            // Handle back button

            val onBackPressedCallback = onBackPressedDispatcher.addCallback(this@MainActivity) {
                model.clearMovie()
            }
            lifecycleScope.launchWhenCreated {

                currentMovieFlow.collect {
                    onBackPressedCallback.isEnabled = it != null
                }
            }


            // Navigation v0.1!

            val currentMovie = currentMovieFlow.collectAsState().value
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
