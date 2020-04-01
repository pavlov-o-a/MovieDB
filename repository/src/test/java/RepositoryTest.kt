import com.companyname.repository.RepositoryFactory
import org.junit.Test

import kotlinx.coroutines.*
import org.junit.Assert

class RepositoryTest {

    @Test
    fun correctMovieResponse(){
        val starWarsId = 181812
        runBlocking {
            val result = RepositoryFactory.getRepository().getMovieRepository().getMovie(starWarsId)
            Assert.assertTrue(result.error?.getMessage()?: "unknown error",result.data != null)
        }
    }

    @Test
    fun correctCreditResponse(){
        val starWarsId = 181812
        runBlocking {
            val result = RepositoryFactory.getRepository().getMovieRepository().getCredits(starWarsId)
            Assert.assertTrue(result.error?.getMessage()?: "unknown error",result.data != null)
        }
    }

    @Test
    fun correctMoviesPageResponse(){
        runBlocking {
            val firstPage = 1
            val result = RepositoryFactory.getRepository().getMoviesRepository().getMovies(firstPage)
            Assert.assertTrue(result.error?.getMessage()?: "unknown error",result.data != null)
        }
    }
}