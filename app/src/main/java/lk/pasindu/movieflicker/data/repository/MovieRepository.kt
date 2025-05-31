package lk.pasindu.movieflicker.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lk.pasindu.movieflicker.data.api.TmdbApiClient
import lk.pasindu.movieflicker.data.api.TmdbApiService
import lk.pasindu.movieflicker.data.model.Genre
import lk.pasindu.movieflicker.data.model.Movie
import retrofit2.HttpException

class MovieRepository(private val api: TmdbApiService) {

    companion object {
        private const val TAG = "MovieRepository"
    }

    suspend fun getMovies(category: String, genreId: String?): List<Movie> {
        return try {
            val response = api.getMoviesByCategory(category, genreId)
            response.results
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getGenres(): List<Genre> {
        return try {
            val response = api.getGenres()
            response.genres
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun searchMovies(query: String): List<Movie> {
        return try {
            val response = api.searchMovies(query = query)
            response.results ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

}
