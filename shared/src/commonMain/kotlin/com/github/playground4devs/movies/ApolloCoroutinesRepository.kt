package com.github.playground4devs.movies

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import kotlinx.coroutines.flow.single

class ApolloCoroutinesRepository {
    private val apolloClient = ApolloClient(
            networkTransport = ApolloHttpNetworkTransport(
                    serverUrl = "https://graphql.imdb.com/index.html",
                    headers = mapOf(
                            "Accept" to "application/json",
                            "Content-Type" to "application/json"
                    )
            )
    )

    suspend fun loadMovies(): List<Movie> {
        val response = apolloClient.query(PopularTitlesQuery()).execute().single()
        return response.data
                ?.popularTitles
                ?.titles
                .orEmpty()
                .map { it.toMovie() }
    }

    private fun PopularTitlesQuery.Title.toMovie(): Movie {
        return Movie(
                titleText?.text.orEmpty(),
                plot?.plotText?.plainText.orEmpty(),
                primaryImage?.toImage(),
                ratingsSummary?.aggregateRating,
                genres?.genres.orEmpty().map { it.text }
        )
    }

    private fun PopularTitlesQuery.PrimaryImage.toImage(): Image? {
        return if (url != null && width != null && height != null && caption?.plainText != null)
            Image(url, width, height, caption.plainText)
        else
            null
    }
}
