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

    fun getMovies(type: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            MoviesRepo.getMovies(type).onStart {
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


class SingleLiveEvent<T> : MutableLiveData<T>()
{
    private val mPending = AtomicBoolean(false)

    @MainThread override fun observe(owner: LifecycleOwner, observer: Observer<in T>)
    {
        if (hasActiveObservers())
        {
//            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false))
            {
                observer.onChanged(t)
            }
        }
    }

    @MainThread override fun setValue(t: T?)
    {
        mPending.set(true)
        super.setValue(t)
    }


    override fun postValue(value: T)
    {
        mPending.set(true)
        super.postValue(value)
    }
}

