package com.github.playground4devs.kmpmovies

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.lazy.LazyColumnItems
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.compose.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.setContent
import dagger.hilt.android.AndroidEntryPoint
import androidx.ui.tooling.preview.Preview
import com.github.playground4devs.kmpmovies.ui.KmpMovieTheme
import com.github.playground4devs.kmpmovies.ui.MainScreen
import com.github.playground4devs.movies.ModelSamples
import com.github.playground4devs.movies.Movie
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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
