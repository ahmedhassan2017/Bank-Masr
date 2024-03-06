package banquemisr.challenge05.ui.movies.home

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.data.remote.ApiResponse
import banquemisr.challenge05.ui.movies.repository.MoviesRepo
import banquemisr.challenge05.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class MoviesViewModel : ViewModel()
{
    val nowPlayingLivedata = MutableLiveData<MovieResponse>()
    val popularLivedata = MutableLiveData<MovieResponse>()
    val upcomingLivedata = MutableLiveData<MovieResponse>()
    val error = SingleLiveEvent<String?>()
    var isLoading = MutableLiveData<Boolean>()
    val repo = MoviesRepo()

    fun getMovies(type: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getMovies(type).onStart {
                isLoading.postValue(true)
            }.catch {
                isLoading.postValue(false)
                error.postValue(it.message)
            }.collect {
                when (type)
                {
                    "now_playing" -> nowPlayingLivedata.postValue(it)
                    "top_rated" -> popularLivedata.postValue(it)
                    "upcoming" -> upcomingLivedata.postValue(it)
                }
                isLoading.postValue(false)
            }
        }
    }
}


