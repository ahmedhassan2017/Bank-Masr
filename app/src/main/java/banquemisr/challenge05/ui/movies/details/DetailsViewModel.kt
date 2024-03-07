package banquemisr.challenge05.ui.movies.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.Models.Movie
import banquemisr.challenge05.Models.MovieDetailsResponse
import banquemisr.challenge05.domain.usecases.getMovieDetails.GetMovieDetailsUseCase
import banquemisr.challenge05.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor (val getMovieDetailsUseCase: GetMovieDetailsUseCase):ViewModel()
{
    val movieDetailsLivedata = MutableLiveData<MovieDetailsResponse>()
    val error = SingleLiveEvent<String?>()
    var isLoading = MutableLiveData<Boolean>()



    fun getMovieDetails(movieId: Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieDetailsUseCase.getMovieDetails(movieId).onStart {
                isLoading.postValue(true)
            }.catch {
                isLoading.postValue(false)
                error.postValue(it.message)
            }.collect {

                movieDetailsLivedata.postValue(it)

                isLoading.postValue(false)
            }
        }
    }

}