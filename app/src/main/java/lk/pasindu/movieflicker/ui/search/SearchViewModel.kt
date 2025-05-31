package lk.pasindu.movieflicker.ui.search

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lk.pasindu.movieflicker.data.api.TmdbApiClient
import lk.pasindu.movieflicker.data.model.Movie
import lk.pasindu.movieflicker.data.repository.MovieRepository

class SearchViewModel : ViewModel() {

    private val repository = MovieRepository(TmdbApiClient.apiService)

    private val _results = MutableLiveData<List<Movie>>()
    val results: LiveData<List<Movie>> get() = _results

    private var searchJob: Job? = null

    fun search(query: String) {
        searchJob?.cancel() // Cancel previous job if still running
        searchJob = viewModelScope.launch {
            delay(300) // Debounce delay to reduce API calls while typing
            if (query.isNotBlank()) {
                try {
                    val result = repository.searchMovies(query)
                    _results.postValue(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _results.postValue(emptyList())
                }
            } else {
                _results.postValue(emptyList())
            }
        }
    }
}

