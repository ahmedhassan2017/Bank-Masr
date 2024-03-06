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
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class MoviesViewModel() : ViewModel()
{
     val nowPlayingLivedata = MutableLiveData<ApiResponse<MovieResponse>?>()
     val popularLivedata = MutableLiveData<ApiResponse<MovieResponse>?>()
     val upcomingLivedata = MutableLiveData<ApiResponse<MovieResponse>?>()
     val error = SingleLiveEvent<String?>()
    var isLoading = MutableLiveData<Boolean>()


    fun getMovies(type:String) {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(false)

            val res = MoviesRepo.getMovies(type)
            if (res.error?.message!=null)
            {
                error.postValue(res.error?.message)
                return@launch
            }
            when (type){
                "now_playing"-> nowPlayingLivedata.postValue(res)
                "top_rated"-> popularLivedata.postValue(res)
                "upcoming"-> upcomingLivedata.postValue(res)
            }

            }
        }
    }



class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
//            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner) { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }
    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }


    override fun postValue(value: T) {
        mPending.set(true)
        super.postValue(value)
    }
}

