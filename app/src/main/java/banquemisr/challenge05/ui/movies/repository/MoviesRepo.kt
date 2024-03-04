package banquemisr.challenge05.ui.movies.repository

import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.data.remote.ApiClient
import banquemisr.challenge05.data.remote.ApiResponse
import banquemisr.challenge05.utils.AppUtils
import banquemisr.challenge05.utils.Constants

object  MoviesRepo
{
      fun getMovies(type:String): ApiResponse<MovieResponse>
    {
        val call = ApiClient.authenticated(Constants.token).service().getMovies(type)
        return ApiResponse.forCall(call)
    }


}