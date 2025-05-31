package lk.pasindu.movieflicker.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import lk.pasindu.movieflicker.BuildConfig
import lk.pasindu.movieflicker.R
import lk.pasindu.movieflicker.databinding.FragmentHomeBinding
import lk.pasindu.movieflicker.ui.adapter.MovieAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieAdapter = MovieAdapter(emptyList()) { selectedMovie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(selectedMovie)
            findNavController().navigate(action)
        }

        Log.d("APIKEY", BuildConfig.TMDB_API_KEY)

        binding.recyclerViewMovies.adapter = movieAdapter

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewMovies.visibility = View.VISIBLE
            movieAdapter.updateData(movies)
        }

        viewModel.loadPopularMovies() // Default

        setupChips()
    }

    private fun setupChips() {
        // When a chip is selected, load the corresponding movies
        binding.chipGroupFilters.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == -1) return@setOnCheckedChangeListener // No chip selected

            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerViewMovies.visibility = View.INVISIBLE
            when (checkedId) {
                R.id.chipPopular -> viewModel.loadPopularMovies()
                R.id.chipTopRated -> viewModel.loadTopRatedMovies()
            }
        }

        // Trigger loading based on initial chip selection (e.g., popular)
        val checkedChipId = binding.chipGroupFilters.checkedChipId
        if (checkedChipId != -1) {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerViewMovies.visibility = View.INVISIBLE
            when (checkedChipId) {
                R.id.chipPopular -> viewModel.loadPopularMovies()
                R.id.chipTopRated -> viewModel.loadTopRatedMovies()
            }
        }
    }
}