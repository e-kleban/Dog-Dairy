package by.kleban.dogdairy.ui.dogpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.database.entities.DbDogWithPosts
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

    private val _dogWithPostsLiveData = MutableLiveData<DbDogWithPosts>()
    val dogWithPostsLiveData: LiveData<DbDogWithPosts>
        get() = _dogWithPostsLiveData

    fun getDog(id: Long) {
        ioScope.launch {
            _dogWithPostsLiveData.postValue(repository.getDogWithPosts(id))
        }
    }
}