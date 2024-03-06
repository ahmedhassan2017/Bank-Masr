package banquemisr.challenge05.domain.usecases

import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.domain.repositories.MoviesRepo
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCaseImp(private val repo: MoviesRepo) :GetMoviesUseCase
{
    override suspend fun getMovies(type: String): Flow<MovieResponse>
    {
        return repo.getMovies(type)
    }
}