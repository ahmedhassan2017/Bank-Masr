package banquemisr.challenge05.ui.movies.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.domain.usecases.GetMoviesUseCase
import banquemisr.challenge05.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel  @Inject constructor(val getMoviesUseCase: GetMoviesUseCase) : ViewModel()
{
    val nowPlayingLivedata = MutableLiveData<MovieResponse>()
    val popularLivedata = MutableLiveData<MovieResponse>()
    val upcomingLivedata = MutableLiveData<MovieResponse>()
    val error = SingleLiveEvent<String?>()
    var isLoading = MutableLiveData<Boolean>()

    fun getMovies(type: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase.getMovies(type).onStart {
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


