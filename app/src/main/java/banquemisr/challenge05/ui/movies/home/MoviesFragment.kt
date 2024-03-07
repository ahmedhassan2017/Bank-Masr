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

    private var loadingView: View? = null
    private var internetView: View? = null
    private var internetText: View? = null
    private var nowPlayingAdapter: MoviesAdapter? = null
    private var popularAdapter: MoviesAdapter? = null
    private var upcomingAdapter: MoviesAdapter? = null
    lateinit var binding: FragmentMoviesBinding

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        loadingView = binding.root.findViewById(R.id.loading_view)
        internetView = binding.root.findViewById(R.id.check_internet_view)
        internetText = binding.root.findViewById(R.id.check_internet_text)
        setupObservers()
        fetchMovies()

       internetText?.setOnClickListener { fetchMovies() }
        return binding.root
    }

    private fun fetchMovies()
    {
        if (isNetworkAvailable(requireContext()))
        {
            internetView?.visibility = View.GONE
            viewModel.getMovies("top_rated")
            viewModel.getMovies("now_playing")
            viewModel.getMovies("upcoming")
        } else {

            internetView?.visibility = View.VISIBLE
        }

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
                if (it?.startsWith("Failed") == true) internetView?.visibility = View.VISIBLE
                else Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }

            viewModel.isLoading.observe(viewLifecycleOwner) {
                if (it) loadingView?.visibility = View.VISIBLE
                else loadingView?.visibility = View.GONE

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

        Constants.selectedMovieId = movie?.id
        navigateToDestination(requireActivity(), R.id.detailesFragment)
    }


}