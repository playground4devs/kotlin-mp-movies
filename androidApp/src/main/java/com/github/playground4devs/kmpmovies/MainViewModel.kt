package com.github.playground4devs.kmpmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.playground4devs.movies.Lce
import com.github.playground4devs.movies.Movie
import com.github.playground4devs.movies.MovieRepository
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _movieList = MutableStateFlow<Lce<List<Movie>>>(Lce.Loading)
    val movieList = _movieList as StateFlow<Lce<List<Movie>>>


    private val _currentMovie = MutableStateFlow<Movie?>(null)
    val currentMovie = _currentMovie as StateFlow<Movie?>

    init {
        viewModelScope.launch {
             MovieRepository().loadMovies().collect {
                 _movieList.value = it
             }
        }
    }

    fun onClickMovie(movie: Movie) {
        _currentMovie.value = movie
    }

    fun clearMovie() {
        _currentMovie.value = null
    }
}