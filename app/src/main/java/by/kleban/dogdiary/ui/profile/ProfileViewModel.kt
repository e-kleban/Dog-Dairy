package by.kleban.dogdiary.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdiary.entities.DogWithPosts
import by.kleban.dogdiary.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: DogRepository
) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _dogWithPostsLiveData = MutableLiveData<DogWithPosts>()
    val dogWithPostsLiveData: LiveData<DogWithPosts>
        get() = _dogWithPostsLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun getDogWithPosts() {
        ioScope.launch {
            try {
                _dogWithPostsLiveData.postValue(repository.getDogWithPosts())
            } catch (e: Exception) {
                Timber.e(e)
                _errorLiveData.postValue(e.message)
            }
        }
    }
}