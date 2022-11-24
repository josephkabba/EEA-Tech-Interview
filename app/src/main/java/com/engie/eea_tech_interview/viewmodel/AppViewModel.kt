package com.engie.eea_tech_interview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.engie.eea_tech_interview.model.Genre
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.remote.MovieApiService
import com.engie.eea_tech_interview.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
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
        emit(Resource.loading())
        val result = movieService.getMovies(MOVIE_API_KEY, SEARCH_QUERY)

        result.onSuccess {
            emit(Resource.success(it.results))
        }.onFailure {
            emit(Resource.error("Network Error"))
        }

    }

    val genres: Flow<Resource<List<Genre>>> = flow {
        emit(Resource.loading())
        val result = movieService.getGenre(MOVIE_API_KEY)

        result.onSuccess {
            emit(Resource.success(it.genres))
        }.onFailure {
            emit(Resource.error("Network Error"))
        }
    }

}