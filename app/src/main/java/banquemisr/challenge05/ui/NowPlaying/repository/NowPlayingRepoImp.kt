package banquemisr.challenge05.ui.NowPlaying.repository

import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.data.remote.ApiClient
import banquemisr.challenge05.data.remote.ApiResponse
import banquemisr.challenge05.utils.AppUtils
import kotlinx.coroutines.flow.Flow

object  NowPlayingRepoImp
{
      fun getNowPlayingMovies(): ApiResponse<MovieResponse>
    {
        val call = ApiClient.authenticated(AppUtils.token).service().getNowPlayingMovies()
        return ApiResponse.forCall(call)
    }


}