package by.kleban.dogdairy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.data.entities.dogbreeds.DogBreed
import by.kleban.dogdairy.repositories.dogbreed.DogBreedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowBreedsViewModel : ViewModel() {

    private val repository = DogBreedRepository.getDogBreedRepository()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _breedListLiveData = MutableLiveData<List<DogBreed>>()
    val breedListLiveData: LiveData<List<DogBreed>>
        get() = _breedListLiveData

    private val _breedListWithFilter = MutableLiveData<List<DogBreed>>()
    val breedListWithFilter: LiveData<List<DogBreed>>
        get() = _breedListWithFilter

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    fun loadListBreed() {
        _isLoadingLiveData.value = true
        ioScope.launch {
            try {
                _isLoadingLiveData.postValue(false)
                _breedListLiveData.postValue(repository.loadBreeds())
            } catch (e: Exception) {
                Log.e(ShowBreedsViewModel::class.java.simpleName, e.message.toString())
            }
        }
    }

    fun filter(string: String) {
        _breedListWithFilter.value =
            _breedListLiveData.value?.filter { it.breed.startsWith(string, true) }
    }

}