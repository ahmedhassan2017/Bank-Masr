package banquemisr.challenge05.domain.usecases

import banquemisr.challenge05.Models.MovieResponse
import kotlinx.coroutines.flow.Flow

interface GetMoviesUseCase
{
    suspend fun getMovies(type:String):Flow<MovieResponse>
}