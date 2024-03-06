package banquemisr.challenge05.ui.movies.repository

import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.data.remote.ApiClient
import banquemisr.challenge05.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object MoviesRepo
{
    suspend fun getMovies(type: String): Flow<MovieResponse>
    {
        return flow {
            emit(ApiClient.authenticated(Constants.token).service().getMovies(type))
        }.flowOn(Dispatchers.IO)
    }
}