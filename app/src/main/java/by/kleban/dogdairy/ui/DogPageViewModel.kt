package by.kleban.dogdairy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.DogDiaryApplication
import by.kleban.dogdairy.repositories.DogRepository
import by.kleban.dogdairy.repositories.DogRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DogPageViewModel : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val repository: DogRepository = DogRepositoryImpl.getDogRepository(DogDiaryApplication.instance)

    private val _idDogLiveData = MutableLiveData<Int>()
    val idDogLiveData: LiveData<Int>
        get() = _idDogLiveData

    init {
        ioScope.launch {
            val dog = repository.getDog()
            _idDogLiveData.postValue(dog?.id)
        }
    }
}