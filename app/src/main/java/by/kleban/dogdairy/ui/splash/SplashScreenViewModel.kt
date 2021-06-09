package by.kleban.dogdairy.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Screen
import by.kleban.dogdairy.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var repository: DogRepository

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
                val dog: Dog? = repository.getDog()
                if (dog != null) {
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