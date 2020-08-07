package com.github.playground4devs.kmpmovies

import androidx.compose.Composable
import androidx.ui.core.ContextAmbient
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.drawBackground
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.ListItem
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import coil.request.GetRequest
import coil.size.Precision
import coil.size.Scale
import com.github.playground4devs.kmpmovies.ui.MainScreen
import com.github.playground4devs.movies.*
import com.github.playground4devs.movies.MovieImagePosition.LEFT
import com.github.playground4devs.movies.MovieImagePosition.TOP
import dev.chrisbanes.accompanist.coil.CoilImage


@Composable
fun MovieListScreen(movieList: Lce<List<Movie>>) =
    MainScreen("Popular Movies & Series") { innerPadding ->
        val navigation = getNavigation()
        when (movieList) {
            is Lce.Success -> if (isTablet())
                CardLayout(
                    movieList.data.toCards(),
                    onClickMovie = { navigation.navigateTo(Screen.Detail(it)) })
            else
                LazyColumnItems(movieList.data) { movie ->
                    MovieItem(movie, showIcon = true, onClickMovie = { navigation.navigateTo(Screen.Detail(it)) })
                }
            is Lce.Loading -> {
                Text("Loading...")
            }
            is Lce.Error -> {
                Text("Error...")
            }
        }
    }

@Composable
fun isTablet() =
    with(DensityAmbient.current) {
        ContextAmbient.current.resources.displayMetrics.widthPixels > 600.dp.toPx()
    }

@Composable
private fun CardLayout(
    card: Card,
    onClickMovie: (Movie) -> Unit
) {
    when (card) {
        is RootCard ->
            LazyColumnItems(card.cards) { group ->
                CardLayout(group, onClickMovie)
                Divider(
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
            }
        is HorizontalGroupCard ->
            Row {
                card.cards.forEach { group ->
                    Column(modifier = Modifier.weight(weight = group.weight)) {
                        CardLayout(group, onClickMovie)
                    }
                }
            }
        is VerticalGroupCard ->
            card.cards.forEachIndexed { index, group ->
                if (index > 0) {
                    Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp))
                }
                CardLayout(group, onClickMovie)
            }
        is MovieCard -> MovieCardItem(card, onClickMovie)
    }
}

@Preview(showBackground = true, widthDp = 1024)
@Composable
fun MovieListScreenPreviewTablet() = MovieListScreen(Lce.Success(ModelSamples.movies))

@Preview(showBackground = true)
@Composable
fun MovieListScreenPreview() = MovieListScreen(Lce.Success(ModelSamples.movies))

@Composable
fun MovieItem(
    movie: Movie,
    showIcon: Boolean = false,
    showPlot: Boolean = true,
    onClickMovie: (Movie) -> Unit = {}
) {
    ListItem(
        icon = showIf(showIcon) {
            CoilImage(
                request = GetRequest.Builder(ContextAmbient.current)
                    .data(movie.image?.url)
                    .size(150, 150)
                    .scale(Scale.FILL)
                    .build()
            )
        },
        text = { Text(movie.title) },
        overlineText = {
            Text(movie.genres.joinToString() + " ⭐️ ${movie.rating ?: "N/A"}")
        },
        secondaryText = showIf(showPlot) { Text(movie.plot) },
        onClick = { onClickMovie(movie) }
    )
}

private fun showIf(
    condition: Boolean,
    composable: @Composable() () -> Unit
): @Composable() (() -> Unit)? {
    return if (condition) {
        composable
    } else null
}

@Composable
fun MovieCardItem(card: MovieCard, onClickMovie: (Movie) -> Unit = {}) {
    if (card.imagePosition == LEFT) {
        Row {
            Column {
                card.movie.image?.let {
                    CoilImage(
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                        request = GetRequest.Builder(ContextAmbient.current)
                            .data(it.url)
                            .size(200, 200)
                            //                        .size(it.width, it.height)
                            .precision(Precision.EXACT)
                            .scale(Scale.FILL)
                            .build()
                    )
                }
            }
            Column {
                MovieItem(card.movie, showPlot = card.showPlot, onClickMovie = onClickMovie)
            }
        }
    } else {
        if (card.imagePosition == TOP) {
            card.movie.image?.let {
                CoilImage(
                    modifier = Modifier.padding(16.dp).fillMaxWidth().aspectRatio(1.5f)
                        .drawBackground(Color.Red),
                    request = GetRequest.Builder(ContextAmbient.current)
                        .data(it.url)
//                        .size(600, 600)
                        //                        .size(it.width, it.height)
                        .precision(Precision.INEXACT)
                        .scale(Scale.FIT)
                        .build()
                )
            }
        }
        MovieItem(card.movie, showPlot = card.showPlot, onClickMovie = onClickMovie)
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() = MovieItem(ModelSamples.movies.first())
