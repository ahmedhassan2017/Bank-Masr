package banquemisr.challenge05.data.remote

import banquemisr.challenge05.Models.MovieResponse
import retrofit2.http.*

/**
 *  API Retrofit interface.
 */
interface ApiInterface {
    /**
     * get movies
     */
    @GET
    suspend fun getMovies(@Url moviesType: String): MovieResponse


}