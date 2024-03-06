package banquemisr.challenge05.ui.movies.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import banquemisr.challenge05.ui.movies.repository.MoviesRepo
import banquemisr.challenge05.utils.AppUtils.navigateToDestination
import banquemisr.challenge05.utils.Constants
import kotlinx.coroutines.launch


class MoviesFragment : Fragment(), OnMovieClickListener
{

    private var nowPlayingAdapter: MoviesAdapter? = null
    private var popularAdapter: MoviesAdapter? = null
    private var upcomingAdapter: MoviesAdapter? = null
    lateinit var binding: FragmentMoviesBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    var nowPlayingRepository: MoviesRepo? = null
    private val viewModel: MoviesViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupObservers()
        viewModel.getMovies("top_rated")
        viewModel.getMovies("now_playing")
        viewModel.getMovies("upcoming")




        return root
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