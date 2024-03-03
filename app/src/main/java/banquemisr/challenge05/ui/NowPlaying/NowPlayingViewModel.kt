package banquemisr.challenge05.ui.NowPlaying

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.data.remote.ApiResponse
import banquemisr.challenge05.domain.repository.NowPlayingRepo
import banquemisr.challenge05.ui.NowPlaying.repository.NowPlayingRepoImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NowPlayingViewModel() : ViewModel()
{
     val nowPlayingMoviesFlow = MutableLiveData<ApiResponse<MovieResponse>?>()

    fun getNowPlayingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = NowPlayingRepoImp.getNowPlayingMovies()
            nowPlayingMoviesFlow.postValue(res)
            }
        }
    }

