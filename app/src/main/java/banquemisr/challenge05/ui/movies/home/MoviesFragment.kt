package banquemisr.challenge05.ui.movies.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import banquemisr.challenge05.R
import banquemisr.challenge05.databinding.FragmentMoviesBinding
import banquemisr.challenge05.ui.movies.repository.MoviesRepo
import banquemisr.challenge05.utils.AppUtils
import banquemisr.challenge05.utils.AppUtils.navigateToDestination
import banquemisr.challenge05.utils.Constants
import kotlinx.coroutines.launch

class MoviesFragment : Fragment()
{

    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var nowPlayingRepository: MoviesRepo? = null
    private val viewModel: MoviesViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        when (Constants.tabTitle)
        {
            "popular" ->
            {
                viewModel.getMovies("popular")
            }
            "play" ->
            {
                viewModel.getMovies("now_playing")
            }
            "upcoming" ->
            {
                viewModel.getMovies("upcoming")
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moviesLivedata.observe(viewLifecycleOwner) { movies ->
//                navigateToDestination(requireActivity(),R.id.detailesFragment)

            }

        }

        return root
    }


    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }


}