package lk.pasindu.movieflicker.data.api

import lk.pasindu.movieflicker.data.model.GenreResponse
import lk.pasindu.movieflicker.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val key = "898d7082467df8e6c1bb0e8146a5f281"

interface TmdbApiService {

    @GET("movie/{category}")
    suspend fun getMoviesByCategory(
        @Path("category") category: String,
        @Query("with_genres") genre: String? = null,
        @Query("api_key") apiKey: String = key
    ): MovieResponse

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = key
    ): GenreResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = key,
        @Query("query") query: String
    ): MovieResponse
}
