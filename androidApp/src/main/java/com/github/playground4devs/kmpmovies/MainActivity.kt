package com.github.playground4devs.kmpmovies

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.setContent
import androidx.ui.viewmodel.viewModel
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
