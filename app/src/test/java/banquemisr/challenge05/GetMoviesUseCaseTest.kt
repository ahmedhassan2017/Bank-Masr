package banquemisr.challenge05

import banquemisr.challenge05.Models.Movie
import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.domain.repositories.MoviesRepo
import banquemisr.challenge05.domain.usecases.getMovies.GetMoviesUseCaseImp
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GetMoviesUseCaseTest
{
    val sampleMovie = Movie(
        adult = false,
        backdropPath = "/backdrop_path.jpg",
        genreIds = listOf(1, 2, 3),
        id = 123,
        originalLanguage = "en",
        originalTitle = "Original Title",
        overview = "Movie overview",
        popularity = 7.8,
        posterPath = "/poster_path.jpg",
        releaseDate = "2022-01-01",
        title = "Movie Title",
        video = false,
        voteAverage = 8.5,
        voteCount = 1000
    )

    @Mock
    private lateinit var mockRepo: MoviesRepo

    private lateinit var useCase: GetMoviesUseCaseImp

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = GetMoviesUseCaseImp(mockRepo)
    }

    @Test
    fun `test getMovies success`() = runBlocking {
        // Mock data
        val type = "popular"
        val expectedResponse = MovieResponse(
            page = 0,
            results = listOf(sampleMovie, sampleMovie, sampleMovie),
            totalPages = 1,
            totalResults = 3
        )

        // Mock behavior
        `when`(mockRepo.getMovies(type)).thenReturn(flowOf(expectedResponse))

        // Execute the use case
        val result = useCase.getMovies(type)

        // Collect the flow and assert the result
        result.collect { actualResponse ->
            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test(expected = Exception::class)
    fun `test getMovies error`() = runBlocking {
        // Mock data
        val type = "popular"
        val exception = Exception("Failed to fetch movies")

        // Mock behavior to throw an exception
        `when`(mockRepo.getMovies(type)).thenThrow(exception)

        var isError = false
        // Execute the use case
        useCase.getMovies(type)
            .catch {
                isError = true
            }
            .collect {
            // This block should not be reached as an exception is expected
                isError = false
        }

        assertEquals(isError, true)
    }
}