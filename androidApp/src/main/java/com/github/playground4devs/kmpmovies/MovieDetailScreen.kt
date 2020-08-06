package com.github.playground4devs.kmpmovies

import android.text.format.DateFormat
import androidx.compose.Composable
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.padding
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.github.playground4devs.kmpmovies.ui.SecondaryScreen
import com.github.playground4devs.kmpmovies.ui.typography
import com.github.playground4devs.movies.ModelSamples
import com.github.playground4devs.movies.Movie
import java.util.*

@Composable
fun MovieDetailScreen(movie: Movie, onArrowBackClick: () -> Unit = {}) =
    SecondaryScreen(movie.title, onArrowBackClick) { innerPadding ->
        Column {
            ConstraintLayout(Modifier.padding(16.dp)) {
                val (GENRE, RATING, PLOT, RELEASE_DATE) = createRefs()

                Text(
                    text = movie.genres.joinToString(),
                    modifier = Modifier.constrainAs(GENRE) {
                        top.linkTo(parent.top)
                    },
                    style = typography.subtitle2
                )

                Text(
                    text = "⭐️ ${movie.rating ?: "N/A"}",
                    modifier = Modifier.constrainAs(RATING) {
                        linkTo(GENRE.top, GENRE.bottom)
                        linkTo(GENRE.end, parent.end, 4.dp, bias = 0f)
                    },
                    style = typography.caption
                )

                Text(
                    text = movie.plot,
                    modifier = Modifier.constrainAs(PLOT) {
                        top.linkTo(GENRE.bottom, margin = 16.dp)
                    },
                    style = typography.h6
                )

                val releaseDate = movie.releaseDate
                    ?.let { DateFormat.getDateFormat(ContextAmbient.current).format(Date(it)) }
                if (releaseDate != null) {
                    Text(
                        text = "Release date: $releaseDate",
                        modifier = Modifier.constrainAs(RELEASE_DATE) {
                            top.linkTo(PLOT.bottom, margin = 16.dp)
                            end.linkTo(parent.end)
                        },
                        style = typography.body2
                    )
                }
            }
        }
    }

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() = MovieDetailScreen(ModelSamples.movies.first())