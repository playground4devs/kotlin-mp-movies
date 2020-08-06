package com.github.playground4devs.kmpmovies

import com.github.playground4devs.movies.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {
    @Provides
    @Singleton
    fun provideRepository() = MovieRepository()
}