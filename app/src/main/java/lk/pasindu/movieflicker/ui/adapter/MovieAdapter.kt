package lk.pasindu.movieflicker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lk.pasindu.movieflicker.R
import lk.pasindu.movieflicker.data.model.Movie
import lk.pasindu.movieflicker.databinding.ItemMovieBinding

class MovieAdapter(
    private var movies: List<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.textViewMovieTitle.text = movie.title
            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .error(R.drawable.default_poster)
                .into(binding.imageViewMoviePoster)

            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateData(newMovies: List<Movie>) {
        this.movies = newMovies
        notifyDataSetChanged()
    }
}
