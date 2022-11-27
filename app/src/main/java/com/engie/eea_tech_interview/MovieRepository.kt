package com.engie.eea_tech_interview

import com.engie.eea_tech_interview.model.GenreResult
import com.engie.eea_tech_interview.model.SearchResult

interface MovieRepository {

    suspend fun getMovies(
       apiKey: String,
       query: String,
    ): Result<SearchResult>


    suspend fun getGenre(
       apiKey: String
    ): Result<GenreResult>
}