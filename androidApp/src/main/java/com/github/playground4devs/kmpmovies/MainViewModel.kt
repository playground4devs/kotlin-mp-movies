package com.github.playground4devs.kmpmovies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.playground4devs.kmpmovies.preview.toConstruct
import com.github.playground4devs.movies.Lce
import com.github.playground4devs.movies.Movie
import com.github.playground4devs.movies.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val DEBUG = false

class MainViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _movieList = MutableStateFlow<Lce<List<Movie>>>(Lce.Loading)
    val movieList = _movieList as StateFlow<Lce<List<Movie>>>

    init {
        viewModelScope.launch {
            repository.loadMovies().collect {
                if (DEBUG) println(toConstruct(it.data))
                _movieList.value = it
            }
        }
    }
}