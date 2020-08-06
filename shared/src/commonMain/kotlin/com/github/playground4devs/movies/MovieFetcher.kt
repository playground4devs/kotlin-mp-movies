package com.github.playground4devs.movies

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import com.soywiz.klock.DateTime
import kotlinx.coroutines.flow.single

class MovieFetcher {
    private val apolloClient = ApolloClient(
        networkTransport = ApolloHttpNetworkTransport(
            serverUrl = "https://graphql.imdb.com/index.html",
            headers = mapOf(
                "Accept" to "application/json",
                "Content-Type" to "application/json"
            )
        )
    )

    suspend fun fetchMovies(): List<Movie> {
        println("Making query")
        val response = apolloClient.query(PopularTitlesQuery()).execute().single()
        println("Response: $response")
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
            genres?.genres.orEmpty().map { it.text },
            releaseDate?.toLong()
        )
    }

    private fun PopularTitlesQuery.PrimaryImage.toImage(): Image? {
        return if (url != null && width != null && height != null && caption?.plainText != null)
            Image(url, width, height, caption.plainText)
        else
            null
    }

    private fun PopularTitlesQuery.ReleaseDate.toLong(): Long? {
        return if (month != null && day != null) {
            DateTime(year, month, day).unixMillisLong
        } else {
            null
        }
    }
}