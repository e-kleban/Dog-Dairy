package by.kleban.dogdairy.ui.dogpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.entities.DogWithPosts
import by.kleban.dogdairy.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogPageViewModel @Inject constructor() : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var repository: DogRepository

    private val _dogWithPostsLiveData = MutableLiveData<DogWithPosts>()
    val dogWithPostsLiveData: LiveData<DogWithPosts>
        get() = _dogWithPostsLiveData

    fun getDogWithPosts(id: Long) {
        ioScope.launch {
            _dogWithPostsLiveData.postValue(repository.getDogWithPosts(id))
        }
    }
}