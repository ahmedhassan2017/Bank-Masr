package banquemisr.challenge05.ui.movies.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import banquemisr.challenge05.R
import banquemisr.challenge05.databinding.FragmentDetailesBinding
import banquemisr.challenge05.ui.movies.details.adapters.GenreAdapter
import banquemisr.challenge05.utils.Constants
import banquemisr.challenge05.utils.formatToOneDecimalPlace
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
                setImage(poster,binding.movieImage)
                setImage(backDrop,binding.backdropImage)
                binding.movieOverview.text = movie?.overview
                binding.movieRate.text = movie?.voteAverage?.formatToOneDecimalPlace().toString()
                binding.movieTitle.text = movie?.originalTitle

                val adapter = GenreAdapter(movie?.genres)
                binding.genreRv.adapter = adapter
                binding.genreRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


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

    private fun setImage(poster: String, movieImage: ImageView)
    {

        Glide.with(requireContext())
            .load(poster)
            .placeholder(R.drawable.ic_launcher_foreground) // Placeholder image while loading
            .error(R.drawable.ic_launcher_foreground) // Image to show if loading fails
            .transition(DrawableTransitionOptions.withCrossFade()) // Fade animation
            .into(movieImage) // Your ImageView instance


    }


}