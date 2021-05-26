package by.kleban.dogdairy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenViewModel : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _startNextFragment = MutableLiveData<Boolean>()
    val startNextFragment: LiveData<Boolean>
        get() = _startNextFragment

    init {
        ioScope.launch {
            delay(4000)
            _startNextFragment.postValue(true)
        }
    }
}