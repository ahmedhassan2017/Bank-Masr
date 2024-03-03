package banquemisr.challenge05.domain.repository

import banquemisr.challenge05.Models.MovieResponse
import kotlinx.coroutines.flow.Flow

interface NowPlayingRepo {

    suspend fun getNowPlayingMovies():Flow<MovieResponse>
}