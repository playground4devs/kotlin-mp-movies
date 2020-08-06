package com.github.playground4devs.kmpmovies.ui

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.InnerPadding
import androidx.ui.layout.padding
import androidx.ui.material.IconButton
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowBack
import androidx.ui.tooling.preview.Preview
import com.github.playground4devs.kmpmovies.MovieItem
import com.github.playground4devs.movies.Movie


@Composable
fun MainScreen(title: String, bodyContent: @Composable (InnerPadding) -> Unit) {
    KmpMovieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) }
                )
            },
            bodyContent = bodyContent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() = MainScreen("Title") { innerPadding ->
    Text("Content", Modifier.padding(innerPadding))
}

@Composable
fun SecondaryScreen(title: String, onArrowBackClick: () -> Unit = {}, bodyContent: @Composable (InnerPadding) -> Unit) =
    KmpMovieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        IconButton(onClick = onArrowBackClick) {
                            Icon(Icons.Filled.ArrowBack)
                        }
                    }
                )
            },
            bodyContent = bodyContent
        )
    }

@Preview(showBackground = true)
@Composable
fun SecondaryScreenPreview() = SecondaryScreen("Title") { innerPadding ->
    Text("Content", Modifier.padding(innerPadding))
}
