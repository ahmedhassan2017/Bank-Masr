package banquemisr.challenge05.domain.usecases.getMovieDetails

import banquemisr.challenge05.Models.Movie
import banquemisr.challenge05.Models.MovieDetailsResponse
import banquemisr.challenge05.Models.MovieResponse
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailsUseCase
{
    suspend fun getMovieDetails(movieId:Int): Flow<MovieDetailsResponse>
}