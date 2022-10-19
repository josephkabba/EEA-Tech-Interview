package com.engie.eea_tech_interview

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("search/multi")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
    ): Call<List<Any>> // TODO: change here after having created a Movie class

}