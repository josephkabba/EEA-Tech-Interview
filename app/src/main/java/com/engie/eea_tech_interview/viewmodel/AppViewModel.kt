package com.engie.eea_tech_interview.viewmodel

import androidx.lifecycle.ViewModel
import com.engie.eea_tech_interview.model.Genre
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.remote.MovieApiService
import com.engie.eea_tech_interview.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val movieService: MovieApiService
): ViewModel() {

    companion object {
        const val MOVIE_API_KEY = "47304f18bd4a3b4e733196b18e68bfbc"
        const val SEARCH_QUERY = "James Bond"
    }

    val movies: Flow<Resource<List<Movie>>> = flow {
        val result = movieService.getMovies(MOVIE_API_KEY, SEARCH_QUERY)
        emit(Resource.loading())

        try {
            if(result.isSuccess){
                val data = result.getOrNull();
                emit(Resource.success(data!!.results))
            }else {
                emit(Resource.error("Network Error"))
            }
        }catch (e: Exception){
            emit(Resource.error("No movies found"))
        }
    }

    val genres: Flow<Resource<List<Genre>>> = flow {
        val result = movieService.getGenre(MOVIE_API_KEY)
        emit(Resource.loading())

        try {
            if(result.isSuccess){
                val data = result.getOrNull();
                emit(Resource.success(data!!.genres))
            }else {
                emit(Resource.error("Network Error"))
            }
        }catch (e: Exception){
            emit(Resource.error("No genres found"))
        }
    }

}