package banquemisr.challenge05.ui.movies.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.Models.MovieResponse
import banquemisr.challenge05.data.remote.ApiResponse
import banquemisr.challenge05.ui.movies.repository.MoviesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel() : ViewModel()
{
     val moviesLivedata = MutableLiveData<ApiResponse<MovieResponse>?>()

    fun getMovies(type:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = MoviesRepo.getMovies(type)
            moviesLivedata.postValue(res)
            }
        }
    }

