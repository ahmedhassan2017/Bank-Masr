package banquemisr.challenge05.ui.movies.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import banquemisr.challenge05.BuildConfig
import banquemisr.challenge05.Models.Movie
import banquemisr.challenge05.R
import banquemisr.challenge05.databinding.MovieItemBinding
import banquemisr.challenge05.utils.formatToOneDecimalPlace
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.squareup.picasso.Picasso

class MoviesAdapter(private var movies: List<Movie>?,
                    private val listener: OnMovieClickListener,

) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movies = movies


//        val poster= "https://api.themoviedb.org${movies?.get(position)?.posterPath}"
        val poster= "https://www.themoviedb.org/t/p/w342${movies?.get(position)?.posterPath}"


//        Picasso.get().load().into(holder.binding.movieImage)

        Glide.with(holder.itemView.context)
            .load(poster)
            .placeholder(R.drawable.ic_launcher_foreground) // Placeholder image while loading
            .error(R.drawable.ic_launcher_background) // Image to show if loading fails
            .transition(DrawableTransitionOptions.withCrossFade()) // Fade animation
            .into(holder.binding.movieImage) // Your ImageView instance


        holder.binding.movieTitle.text = movies?.get(position)?.title
        holder.binding.movieReleaseDate.text = movies?.get(position)?.releaseDate
        holder.binding.movieRate.text = movies?.get(position)?.voteAverage?.formatToOneDecimalPlace().toString()


        holder.itemView.setOnClickListener {
            listener.onItemClicked(position, movies?.get(position))
        }
    }



    override fun getItemCount(): Int {
        return movies?.size ?:0
    }

    // this solution for Dispense notifyDataSetChanged()
    fun updateData(newData: List<Movie>?) {
        //compare the current data and newData and call the appropriate notify method
        val diff = DiffUtil.calculateDiff(CartDiffCallback(movies, newData))
        movies = newData
        diff.dispatchUpdatesTo(this)
    }

    class CartDiffCallback(
            private val oldList: List<Movie>?,
            private val newList: List<Movie>?
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList?.get(oldItemPosition) == newList?.get(newItemPosition)
        }

        override fun getOldListSize(): Int {
            return oldList?.size?:0
        }

        override fun getNewListSize(): Int {
            return newList?.size?:0
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList?.get(oldItemPosition) == (newList?.get(newItemPosition) ?: "")
        }
    }



}

