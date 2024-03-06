package banquemisr.challenge05.ui.movies.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import banquemisr.challenge05.Models.Movie
import banquemisr.challenge05.R
import banquemisr.challenge05.databinding.FragmentMoviesBinding
import banquemisr.challenge05.ui.movies.home.adapters.MoviesAdapter
import banquemisr.challenge05.ui.movies.home.adapters.OnMovieClickListener
import banquemisr.challenge05.data.repository.MoviesRepoImp
import banquemisr.challenge05.utils.AppUtils.navigateToDestination
import banquemisr.challenge05.utils.Constants
import banquemisr.challenge05.utils.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesFragment : Fragment(), OnMovieClickListener
{

    private var nowPlayingAdapter: MoviesAdapter? = null
    private var popularAdapter: MoviesAdapter? = null
    private var upcomingAdapter: MoviesAdapter? = null
    lateinit var binding: FragmentMoviesBinding

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        if (isNetworkAvailable(requireContext()))
        {
            viewModel.getMovies("top_rated")
            viewModel.getMovies("now_playing")
            viewModel.getMovies("upcoming")
        } else
        {
            Toast.makeText(requireContext(), "Check your network connection", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        setupObservers()

        return binding.root
    }

    private fun setupObservers()
    {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nowPlayingLivedata.observe(viewLifecycleOwner) { movies ->
                nowPlayingAdapter = MoviesAdapter(movies?.results, this@MoviesFragment)
                presentData(nowPlayingAdapter, movies?.results, binding.nowPlayingRv)

            }
            viewModel.popularLivedata.observe(viewLifecycleOwner) { movies ->
                popularAdapter = MoviesAdapter(movies?.results, this@MoviesFragment)
                presentData(popularAdapter, movies?.results, binding.popularRv)

            }
            viewModel.upcomingLivedata.observe(viewLifecycleOwner) { movies ->
                upcomingAdapter = MoviesAdapter(movies?.results, this@MoviesFragment)
                presentData(upcomingAdapter, movies?.results, binding.upcomingRv)

            }

            viewModel.error.observe(viewLifecycleOwner) {
                Log.i("TAG", "error: $it")
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun presentData(adapter: MoviesAdapter?, moviesList: List<Movie>?, recycler: RecyclerView)
    {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        adapter?.updateData(moviesList)

    }

    override fun onItemClicked(position: Int?, movie: Movie?)
    {

        Log.i("TAG", "onItemClicked: position : $position \n movie title : ${movie?.title}")

        Constants.selectedMovie = movie
        navigateToDestination(requireActivity(), R.id.detailesFragment)
    }


}