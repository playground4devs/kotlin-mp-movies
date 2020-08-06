package com.github.playground4devs.kmpmovies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.playground4devs.movies.Lce
import com.github.playground4devs.movies.Movie
import com.github.playground4devs.movies.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _movieList = MutableStateFlow<Lce<List<Movie>>>(Lce.Loading)
    val movieList = _movieList as StateFlow<Lce<List<Movie>>>


    private val _currentMovie = MutableStateFlow<Movie?>(null)
    val currentMovie = _currentMovie as StateFlow<Movie?>

    init {
        viewModelScope.launch {
            repository.loadMovies().collect {
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