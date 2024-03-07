package banquemisr.challenge05.data.remote

import banquemisr.challenge05.Models.Movie
import banquemisr.challenge05.Models.MovieDetailsResponse
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

    /**
     * get movie details
     */

    @GET("{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetailsResponse

}