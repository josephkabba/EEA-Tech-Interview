package com.engie.eea_tech_interview.utils

import com.engie.eea_tech_interview.model.Genre
import com.engie.eea_tech_interview.model.Movie
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NavJsonConvertor {

    private var movieJsonAdapter: JsonAdapter<Movie>
    private var genreJsonAdapter: JsonAdapter<Genre>

    init {
        val moshi = Moshi.Builder().build()
        movieJsonAdapter = moshi.adapter(Movie::class.java).lenient()
        genreJsonAdapter = moshi.adapter(Genre::class.java).lenient()
    }

    fun movieToJson(movie: Movie): String {

        return movieJsonAdapter.toJson(
            movie.copy(
                posterPath = if (!movie.posterPath.isNullOrBlank()) {
                    URLEncoder.encode(
                        movie.posterPath,
                        StandardCharsets.UTF_8.toString()
                    )
                } else {
                    null
                }
            )
        )
    }

    fun movieFromJson(data: String): Movie? = movieJsonAdapter.fromJson(data)
}