package by.kleban.dogdairy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.DogDiaryApplication
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Screen
import by.kleban.dogdairy.repositories.DogRepository
import by.kleban.dogdairy.repositories.DogRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenViewModel : ViewModel() {

    private val repository: DogRepository = DogRepositoryImpl.getDogBreedRepository(DogDiaryApplication.instance)
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _nextFragmentLiveData = MutableLiveData<Screen>()
    val nextFragmentLiveData: LiveData<Screen>
        get() = _nextFragmentLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    init {
        ioScope.launch {
            delay(3000)
            try {
                val dogOrEmpty: Dog? = repository.getDog()
                if (dogOrEmpty != null) {
                    _nextFragmentLiveData.postValue(Screen.DOG)
                } else {
                    _nextFragmentLiveData.postValue(Screen.REGISTRATION)
                }
            } catch (e: Exception) {
                _errorLiveData.postValue(e.message)
            }
        }
    }
}