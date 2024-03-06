package banquemisr.challenge05.ui.movies.repository

import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.data.remote.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepo @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getMovies(type: String): Flow<MovieResponse> {
        return flow {
            emit(apiInterface.getMovies(type))
        }.flowOn(Dispatchers.IO)
    }
}