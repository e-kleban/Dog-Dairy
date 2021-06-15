package by.kleban.dogdairy.ui.onepost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.entities.Post
import by.kleban.dogdairy.entities.Validation
import by.kleban.dogdairy.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnePostViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    @Inject
    lateinit var repository: DogRepository

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _postLiveData = savedStateHandle.getLiveData<Post>(OnePostFragment.ONE_POST)
    val postLiveData: LiveData<Post>
        get() = _postLiveData

    private val _validationDescriptionLiveData = MutableLiveData<Validation>()
    val validationDescriptionLiveData: LiveData<Validation>
        get() = _validationDescriptionLiveData

    private var description: String? = null

    fun updateDescription(description: String?) {
        this.description = description
    }

    private fun validateDescription(): Validation {
        return when {
            description.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    fun updatePost() {
        _validationDescriptionLiveData.value = validateDescription()
        val validationDescription = _validationDescriptionLiveData.value
        if (validationDescription == Validation.VALID) {
            ioScope.launch {
                try {
                    val oldPost = _postLiveData.value!!
                    val newPost = oldPost.copy(description = description!!)
                    repository.updatePost(newPost)
                    withContext(Dispatchers.Main) {
                        savedStateHandle.set(OnePostFragment.ONE_POST, newPost)
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    fun deletePost() {
        ioScope.launch {
            repository.deletePost(_postLiveData.value?.image!!)
        }
    }
}