package banquemisr.challenge05.ui.NowPlaying

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import banquemisr.challenge05.databinding.FragmentNowplayingBinding
import banquemisr.challenge05.ui.NowPlaying.repository.NowPlayingRepoImp
import kotlinx.coroutines.launch

class NowPlayingFragment : Fragment()
{

    private var _binding: FragmentNowplayingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var nowPlayingRepository: NowPlayingRepoImp? = null
    private val viewModel: NowPlayingViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
//        nowPlayingRepository = NowPlayingRepoImp()
//        val factory = NowPlayingViewModelFactory(nowPlayingRepository!!)
//        viewModel = ViewModelProvider(this, factory)[NowPlayingViewModel::class.java]


        _binding = FragmentNowplayingBinding.inflate(inflater, container, false)
        val root: View = binding.root


        viewModel.getNowPlayingMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nowPlayingMoviesFlow.observe(viewLifecycleOwner) { movies ->
                Log.i("TAG", "\n\nonCreateView: moviesList :${movies?.data?.page}\n\n")
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