package banquemisr.challenge05.domain.usecases.getMovieDetails

import banquemisr.challenge05.Models.Movie
import banquemisr.challenge05.Models.MovieDetailsResponse
import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.domain.repositories.MoviesRepo
import kotlinx.coroutines.flow.Flow

class GetMovieDetailsUseCaseImp(private val repo: MoviesRepo):GetMovieDetailsUseCase
{
    override suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse>
    {
        return repo.getMovieDetails(movieId)
    }

}
