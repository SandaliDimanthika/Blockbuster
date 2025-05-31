package lk.pasindu.movieflicker.ui.home

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import lk.pasindu.movieflicker.data.api.TmdbApiClient
import lk.pasindu.movieflicker.data.model.Movie
import lk.pasindu.movieflicker.data.repository.MovieRepository

class HomeViewModel : ViewModel() {

    private val repository = MovieRepository(TmdbApiClient.apiService)

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun loadPopularMovies() {
        viewModelScope.launch {
            try {
                _movies.value = repository.getMovies("popular", null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadTopRatedMovies() {
        viewModelScope.launch {
            try {
                _movies.value = repository.getMovies("top_rated", null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
