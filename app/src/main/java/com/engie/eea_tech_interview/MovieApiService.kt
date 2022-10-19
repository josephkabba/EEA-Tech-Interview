package com.engie.eea_tech_interview

import com.engie.eea_tech_interview.model.GenreResult
import com.engie.eea_tech_interview.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("search/movie")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
    ): Call<SearchResult>

    @GET("genre/movie/list")
    fun getGenre(
        @Query("api_key") apiKey: String
    ): Call<GenreResult>

}