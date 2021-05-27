package by.kleban.dogdairy

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

    fun loadListBreed() {
        ioScope.launch {
            try {
                _breedListLiveData.postValue(repository.loadBreeds())
            } catch (e: Exception) {

            }
        }
    }

}