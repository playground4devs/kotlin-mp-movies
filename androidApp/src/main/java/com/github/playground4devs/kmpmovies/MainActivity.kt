package com.github.playground4devs.kmpmovies

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.ui.animation.Crossfade
import androidx.ui.core.setContent
import androidx.ui.material.Divider
import androidx.ui.viewmodel.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navigation = getNavigation()

            // Handle back button
            val onBackPressedCallback = onBackPressedDispatcher.addCallback(this@MainActivity) {
                navigation.onBack()
            }
            lifecycleScope.launchWhenCreated {
                navigation.currentScreen.collect {
                    onBackPressedCallback.isEnabled = it != Screen.Home
                }
            }
            Divider()


            // Navigation v0.2!

            Crossfade(navigation.currentScreen.collectAsState().value) { screen ->
                when (screen) {
                    Screen.Home -> MovieListScreen(
                        movieList = model.movieList.collectAsState().value
                    )
                    is Screen.Detail -> MovieDetailScreen(
                        movie = screen.movie
                    )
                }
            }
        }
    }
}

