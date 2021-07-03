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
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: DogRepository
) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _dogWithPostsLiveData = MutableLiveData<DogWithPosts>()
    val dogWithPostsLiveData: LiveData<DogWithPosts>
        get() = _dogWithPostsLiveData

    fun getDogWithPosts() {
        ioScope.launch {
            _dogWithPostsLiveData.postValue(repository.getDogWithPosts())
        }
    }
}