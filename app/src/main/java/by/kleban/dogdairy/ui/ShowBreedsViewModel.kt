package by.kleban.dogdairy.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.DogDiaryApplication
import by.kleban.dogdairy.entities.DogBreed
import by.kleban.dogdairy.repositories.DogRepository
import by.kleban.dogdairy.repositories.DogRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowBreedsViewModel : ViewModel() {

    private val repository: DogRepository = DogRepositoryImpl.getDogBreedRepository(DogDiaryApplication.instance)
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

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun loadListBreed() {
        _isLoadingLiveData.value = true
        ioScope.launch {
            try {
                _breedListLiveData.postValue(repository.loadBreeds())
                _isLoadingLiveData.postValue(false)
            } catch (e: Exception) {
                Log.e(ShowBreedsViewModel::class.java.simpleName, e.message.toString())
                _isLoadingLiveData.postValue(false)
                _errorLiveData.postValue(e.message)
            }
        }
    }

    fun filter(string: String) {
        _breedListWithFilter.value =
            _breedListLiveData.value?.filter { it.breed.startsWith(string, true) }
    }

}