package banquemisr.challenge05.data.remote

import banquemisr.challenge05.Models.MovieResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * dKilo API Retrofit interface.
 */
interface ApiInterface {
    /**
     * Check app status (for version check if there is an update).
     */
    @GET("now_playing")
     fun getNowPlayingMovies(): Call<MovieResponse>

}