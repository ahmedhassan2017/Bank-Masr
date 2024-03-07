package banquemisr.challenge05.ui.movies.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import banquemisr.challenge05.R
import banquemisr.challenge05.databinding.FragmentDetailesBinding
import banquemisr.challenge05.databinding.FragmentMoviesBinding
import banquemisr.challenge05.ui.movies.home.MoviesViewModel
import banquemisr.challenge05.ui.movies.home.adapters.MoviesAdapter
import banquemisr.challenge05.utils.Constants
import banquemisr.challenge05.utils.isNetworkAvailable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailsFragment : Fragment()
{
    lateinit var binding: FragmentDetailesBinding
    private val viewModel: DetailsViewModel  by viewModels()
    private var loadingView: View? = null
    private var internetView: View? = null
    private var internetText: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentDetailesBinding.inflate(inflater, container, false)
        loadingView = binding.root.findViewById(R.id.loading_view)
        internetView = binding.root.findViewById(R.id.check_internet_view)
        internetText = binding.root.findViewById(R.id.check_internet_text)

        setupObservers()
        fetchMovieDetails()
        internetText?.setOnClickListener { fetchMovieDetails() }

        return binding.root

    }

    private fun fetchMovieDetails()
    {
        if (isNetworkAvailable(requireContext()))
        {
            internetView?.visibility = View.GONE
            Constants.selectedMovieId?.let { viewModel.getMovieDetails(it) }

        } else internetView?.visibility = View.VISIBLE

    }


    private fun setupObservers()
    {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieDetailsLivedata.observe(viewLifecycleOwner) { movie ->
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

            }



            viewModel.error.observe(viewLifecycleOwner) {
                if (it?.startsWith("Failed") == true) internetView?.visibility = View.VISIBLE
                else Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }

            viewModel.isLoading.observe(viewLifecycleOwner) {
                if (it) loadingView?.visibility = View.VISIBLE
                else loadingView?.visibility = View.GONE

            }

        }

    }



}