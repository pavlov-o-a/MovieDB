import com.companyname.repository.types.movie.MovieRepository
import com.companyname.repository.types.movie.MovieRepositoryImp
import com.companyname.repository.types.movies.MoviesRepository
import com.companyname.repository.types.movies.MoviesRepositoryImpl
import org.junit.Test

import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before

class RepositoryTest {
    private lateinit var movieRepository: MovieRepository
    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun initReps() {
        movieRepository = MovieRepositoryImp()
        moviesRepository = MoviesRepositoryImpl()
    }

    @Test
    fun correctMovieResponse() {
        val starWarsId = 181812
        runBlocking {
            val result = movieRepository.getMovie(starWarsId)
            Assert.assertTrue(result.error?.getMessage() ?: "unknown error", result.data != null)
        }
    }

    @Test
    fun correctCreditResponse() {
        val starWarsId = 181812
        runBlocking {
            val result = movieRepository.getCredits(starWarsId)
            Assert.assertTrue(result.error?.getMessage() ?: "unknown error", result.data != null)
        }
    }

    @Test
    fun correctMoviesPageResponse() {
        runBlocking {
            val firstPage = 1
            val result = moviesRepository.getMovies(firstPage)
            Assert.assertTrue(result.error?.getMessage() ?: "unknown error", result.data != null)
        }
    }
}