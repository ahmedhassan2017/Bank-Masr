package banquemisr.challenge05.ui.movies.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import banquemisr.challenge05.domain.repository.NowPlayingRepo

class MoviesViewModelFactory(private val nowPlayingRepo: NowPlayingRepo): ViewModelProvider.NewInstanceFactory() {
    // means that the viewModel will be created by the NowPlayingViewModelFactory
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModelFactory(nowPlayingRepo) as T
    }
}