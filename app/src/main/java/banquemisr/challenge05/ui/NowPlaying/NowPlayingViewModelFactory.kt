package banquemisr.challenge05.ui.NowPlaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import banquemisr.challenge05.domain.repository.NowPlayingRepo
import banquemisr.challenge05.ui.NowPlaying.repository.NowPlayingRepoImp

class NowPlayingViewModelFactory(private val nowPlayingRepo: NowPlayingRepo): ViewModelProvider.NewInstanceFactory() {
    // means that the viewModel will be created by the NowPlayingViewModelFactory
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NowPlayingViewModelFactory(nowPlayingRepo) as T
    }
}