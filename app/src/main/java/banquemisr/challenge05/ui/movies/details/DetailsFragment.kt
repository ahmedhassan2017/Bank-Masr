package banquemisr.challenge05.ui.movies.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import banquemisr.challenge05.R
import banquemisr.challenge05.databinding.FragmentDetailesBinding
import banquemisr.challenge05.databinding.FragmentMoviesBinding
import banquemisr.challenge05.utils.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class DetailsFragment : Fragment()
{
    lateinit var binding: FragmentDetailesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentDetailesBinding.inflate(inflater, container, false)

        val movie = Constants.selectedMovie
        val poster= "https://www.themoviedb.org/t/p/w342${movie?.posterPath}"
        val backDrop= "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2${movie?.backdropPath}"

        Glide.with(requireContext())
            .load(poster)
            .placeholder(R.drawable.ic_launcher_foreground) // Placeholder image while loading
            .error(R.drawable.ic_launcher_background) // Image to show if loading fails
            .transition(DrawableTransitionOptions.withCrossFade()) // Fade animation
            .into(binding.movieImage) // Your ImageView instance


        Glide.with(requireContext())
            .load(backDrop)
            .placeholder(R.drawable.ic_launcher_foreground) // Placeholder image while loading
            .error(R.drawable.ic_launcher_background) // Image to show if loading fails
            .transition(DrawableTransitionOptions.withCrossFade()) // Fade animation
            .into(binding.backdropImage) // Your ImageView instance


        binding.movieOverview.text = movie?.overview
        binding.moviePopularity.text = movie?.popularity?.toInt().toString()
        binding.movieRate.text = movie?.voteAverage?.toInt().toString()
        binding.movieTitle.text = movie?.originalTitle
        binding.originalLang.text = movie?.originalLanguage
        binding.movieVoteCount.text = movie?.voteCount.toString()



        return binding.root

    }


}