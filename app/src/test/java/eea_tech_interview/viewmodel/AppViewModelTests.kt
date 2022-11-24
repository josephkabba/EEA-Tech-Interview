package eea_tech_interview.viewmodel

import com.engie.eea_tech_interview.model.Genre
import com.engie.eea_tech_interview.model.GenreResult
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.model.SearchResult
import com.engie.eea_tech_interview.remote.MovieApiService
import com.engie.eea_tech_interview.utils.Resource
import com.engie.eea_tech_interview.viewmodel.AppViewModel
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTests {


    private lateinit var movieApiService: MovieApiService

    private lateinit var appViewModel: AppViewModel

    private lateinit var moviesSearchResult: SearchResult

    private lateinit var movieGenresResult: GenreResult


    private val apiKey = "apiKey"
    private val query = "captain america"


    @Before
    fun setUp() {
        moviesSearchResult = mock()
        movieGenresResult = mock()
        movieApiService = mock()
        appViewModel = AppViewModel(movieApiService)
    }


    @Test
    fun test_getMoviesFromNetWorkRepository() = runTest {
        //arrange
        val expected = flow {
            emit(Resource.loading())
            delay(10)
            emit(Resource.success(moviesSearchResult.results))
        }

        val result = Result.success(moviesSearchResult)
        movieApiService.stub {
            onBlocking { getMovies(apiKey, query) } doReturn result
        }

        //act
        val actual = appViewModel.movies

        //assert
        assertEquals(expected.first().status, actual.first().status)
        assertEquals(expected.last().status, actual.last().status)
        assertEquals(expected.last().data, actual.last().data)
        verify(movieApiService).getMovies(any(), any())
    }


    @Test
    fun test_getMoviesGenresFromNetWorkRepository() = runTest {
        //arrange
        val expected = flow {
            emit(Resource.loading())
            delay(10)
            emit(Resource.success(movieGenresResult.genres))
        }

        val result = Result.success(movieGenresResult)
        movieApiService.stub {
            onBlocking { getGenre(apiKey) } doReturn result
        }

        //act
        val actual = appViewModel.genres

        //assert
        assertEquals(expected.first().status, actual.first().status)
        assertEquals(expected.last().data, actual.last().data)
        assertEquals(expected.last().status, actual.last().status)
        verify(movieApiService).getGenre(any())
    }


    @Test
    fun test_getMoviesGenresFromNetWorkRepositoryFailed() = runTest {
        //arrange
        val expected = flow {
            emit(Resource.loading())
            delay(10)
            emit(Resource.error<Genre>("Network Error"))
        }

        val result = Result.failure<GenreResult>(Exception())
        movieApiService.stub {
            onBlocking { getGenre(apiKey) } doReturn result
        }

        //act
        val actual = appViewModel.genres

        //assert
        assertEquals(expected.first().status, actual.first().status)
        assertEquals(expected.last().message, actual.last().message)
        assertEquals(expected.last().status, actual.last().status)
        verify(movieApiService).getGenre(any())
    }

    @Test
    fun test_getMoviesFromNetWorkRepositoryFailed() = runTest {
        //arrange
        val expected = flow {
            emit(Resource.loading())
            delay(10)
            emit(Resource.error<Movie>("Network Error"))
        }

        val result = Result.failure<SearchResult>(Exception())
        movieApiService.stub {
            onBlocking { getMovies(apiKey, query) } doReturn result
        }

        //act
        val actual = appViewModel.movies

        //assert
        assertEquals(expected.first().status, actual.first().status)
        assertEquals(expected.last().message, actual.last().message)
        assertEquals(expected.last().status, actual.last().status)
        verify(movieApiService).getMovies(any(), any())
    }

}