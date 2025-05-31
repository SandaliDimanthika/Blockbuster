package lk.pasindu.movieflicker.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import lk.pasindu.movieflicker.R
import lk.pasindu.movieflicker.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs() // Safe Args to get movie passed

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movie = args.movie

        binding.textTitle.text = movie.title
        binding.textReleaseYear.text = movie.releaseDate.take(4)
        binding.textOverview.text = movie.overview

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .error(R.drawable.default_poster)
            .into(binding.imagePoster)

        // Ask ViewModel to fetch genre names
        viewModel.loadGenresForMovie(movie)

        // Observe genre names and display
        viewModel.genreNames.observe(viewLifecycleOwner) { genreText ->
            binding.textGenre.text = genreText
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
