package com.engie.eea_tech_interview.data

import com.engie.eea_tech_interview.MovieRepository
import com.engie.eea_tech_interview.model.GenreResult
import com.engie.eea_tech_interview.model.SearchResult
import com.engie.eea_tech_interview.remote.MovieApiService
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieApiService: MovieApiService) :
    MovieRepository {
    override suspend fun getMovies(apiKey: String, query: String): Result<SearchResult> {
        return movieApiService.getMovies(apiKey, query)
    }

    override suspend fun getGenre(apiKey: String): Result<GenreResult> {
        return movieApiService.getGenre(apiKey)
    }
}