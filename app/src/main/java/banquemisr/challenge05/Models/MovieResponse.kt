package banquemisr.challenge05.Models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class MovieResponse(
        val dates: Dates,
        val page: Int,
        val results: List<Movie>,
        @Json(name = "total_pages")
        val totalPages: Int,
        @Json(name = "total_results")
        val totalResults: Int
)
@JsonClass(generateAdapter = true)
data class Dates(
        val maximum: String,
        val minimum: String
)
@JsonClass(generateAdapter = true)
data class Movie(
        val adult: Boolean,
        @Json(name ="backdrop_path")
        val backdropPath: String?,
        @Json(name ="genre_ids")
        val genreIds: List<Int>,
        val id: Int,
        @Json(name ="original_language")
        val originalLanguage: String,
        @Json(name ="original_title")
        val originalTitle: String,
        val overview: String,
        val popularity: Double,
        @Json(name ="poster_path")
        val posterPath: String?,
        @Json(name ="release_date")
        val releaseDate: String,
        val title: String,
        val video: Boolean,
        @Json(name ="vote_average")
        val voteAverage: Double,
        @Json(name ="vote_count")
        val voteCount: Int
)
