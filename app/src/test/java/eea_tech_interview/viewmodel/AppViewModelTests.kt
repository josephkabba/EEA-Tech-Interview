package eea_tech_interview.viewmodel

import com.engie.eea_tech_interview.MovieRepository
import com.engie.eea_tech_interview.model.Genre
import com.engie.eea_tech_interview.model.GenreResult
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.model.SearchResult
import com.engie.eea_tech_interview.utils.Resource
import com.engie.eea_tech_interview.viewmodel.AppViewModel
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class AppViewModelTests {

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var appViewModel: AppViewModel

    @Mock
    private lateinit var moviesSearchResult: SearchResult

    @Mock
    private lateinit var movieGenresResult: GenreResult

    private val apiKey = "apiKey"
    private val query = "captain america"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        appViewModel = AppViewModel(movieRepository)
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
        whenever(movieRepository.getMovies(apiKey,query)).thenReturn(result)

        //act
        val actual = appViewModel.movies

        //assert
        assertEquals(expected.first().status, actual.first().status)
        assertEquals(expected.last().status, actual.last().status)
        assertEquals(expected.last().data, actual.last().data)
        verify(movieRepository).getMovies(any(), any())
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
        whenever(movieRepository.getGenre(apiKey)).thenReturn(result)

        //act
        val actual = appViewModel.genres

        //assert
        assertEquals(expected.first().status, actual.first().status)
        assertEquals(expected.last().data, actual.last().data)
        assertEquals(expected.last().status, actual.last().status)
        verify(movieRepository).getGenre(any())
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
        whenever(movieRepository.getGenre(apiKey)).thenReturn(result)

        //act
        val actual = appViewModel.genres

        //assert
        assertEquals(expected.first().status, actual.first().status)
        assertEquals(expected.last().message, actual.last().message)
        assertEquals(expected.last().status, actual.last().status)
        verify(movieRepository).getGenre(any())
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
        whenever(movieRepository.getMovies(apiKey, query)).thenReturn(result)

        //act
        val actual = appViewModel.movies

        //assert
        assertEquals(expected.first().status, actual.first().status)
        assertEquals(expected.last().message, actual.last().message)
        assertEquals(expected.last().status, actual.last().status)
        verify(movieRepository).getMovies(any(), any())
    }

}