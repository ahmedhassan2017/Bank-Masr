package banquemisr.challenge05.domain.repositories

import banquemisr.challenge05.Models.Movie
import banquemisr.challenge05.Models.MovieDetailsResponse
import banquemisr.challenge05.Models.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepo {

    suspend fun getMovies(type: String): Flow<MovieResponse>
    suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse>
}