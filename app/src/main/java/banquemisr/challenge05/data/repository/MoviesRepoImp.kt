package banquemisr.challenge05.data.repository

import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.data.remote.ApiInterface
import banquemisr.challenge05.domain.repositories.MoviesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepoImp @Inject constructor(private val apiInterface: ApiInterface):MoviesRepo {
    override suspend fun getMovies(type: String): Flow<MovieResponse> {
        return flow {
            emit(apiInterface.getMovies(type))
        }.flowOn(Dispatchers.IO)
    }
}