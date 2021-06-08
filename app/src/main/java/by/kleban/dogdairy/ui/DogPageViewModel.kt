package by.kleban.dogdairy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.DogDiaryApplication
import by.kleban.dogdairy.database.entities.DbDogWithPosts
import by.kleban.dogdairy.entities.Validation
import by.kleban.dogdairy.repositories.DogRepository
import by.kleban.dogdairy.repositories.DogRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DogPageViewModel : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val repository: DogRepository = DogRepositoryImpl.getDogRepository(DogDiaryApplication.instance)

    private val _dogWithPostsLiveData = MutableLiveData<DbDogWithPosts>()
    val dogWithPostsLiveData: LiveData<DbDogWithPosts>
        get() = _dogWithPostsLiveData

    fun getDog(id:Long){
        ioScope.launch {
           _dogWithPostsLiveData.postValue(repository.getDogWithPosts(id))
        }
    }
}