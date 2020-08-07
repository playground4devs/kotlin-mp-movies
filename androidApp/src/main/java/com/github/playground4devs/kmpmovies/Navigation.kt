package com.github.playground4devs.kmpmovies;

import androidx.compose.Composable
import androidx.lifecycle.ViewModel
import androidx.ui.viewmodel.viewModel
import com.github.playground4devs.movies.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class Screen {
    object Home : Screen()
    data class Detail(val movie: Movie) : Screen()
}

class NavigationViewModel : ViewModel() {
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen = _currentScreen as StateFlow<Screen>

    fun onBack(): Boolean {
        val wasHandled = currentScreen.value != Screen.Home
        _currentScreen.value = Screen.Home
        return wasHandled
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }
}

@Composable
fun getNavigation() = viewModel<NavigationViewModel>()