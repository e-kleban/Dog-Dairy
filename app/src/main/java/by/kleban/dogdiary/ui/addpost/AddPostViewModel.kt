package by.kleban.dogdiary.ui.addpost

import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdiary.entities.Post
import by.kleban.dogdiary.entities.SharedConfig
import by.kleban.dogdiary.entities.Validation
import by.kleban.dogdiary.helper.FileHelper
import by.kleban.dogdiary.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val fileHelper: FileHelper,
    private val repository: DogRepository
) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _descriptionPostLiveData = MutableLiveData<String>()
    val descriptionPostLiveData: LiveData<String>
        get() = _descriptionPostLiveData

    private val _validationImageLiveData = MutableLiveData<Validation>()
    val validationImageLiveData: LiveData<Validation>
        get() = _validationImageLiveData

    private val _validationDescriptionLiveData = MutableLiveData<Validation>()
    val validationDescriptionLiveData: LiveData<Validation>
        get() = _validationDescriptionLiveData

    private val _isSavedPostLiveData = MutableLiveData(false)
    val isSavedPostLiveData: LiveData<Boolean>
        get() = _isSavedPostLiveData

    private val _imageLiveData = MutableLiveData<Uri>()
    val imageLiveData: LiveData<Uri>
        get() = _imageLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun saveDescription(description: String) {
        _descriptionPostLiveData.value = description
    }

    fun chooseImage(uri: Uri) {
        _imageLiveData.value = uri
    }

    fun addPost() {
        val validateImage = validateImage()
        val validateDescription = validateDescription()
        _validationImageLiveData.value = validateImage
        _validationDescriptionLiveData.value = validateDescription
        if (validateImage == Validation.VALID &&
            validateDescription == Validation.VALID
        ) {
            val dogCreatorId = sharedPreferences.getLong(SharedConfig.SHARED_PREF_DOG_ID, 0)
            ioScope.launch {
                try {
                    val (image, thumb) = saveImages()
                    val newPost = createPost(dogCreatorId, image, thumb)
                    repository.savePost(newPost)
                    _isSavedPostLiveData.postValue(true)
                } catch (e: Exception) {
                    Timber.e(e)
                    _errorLiveData.postValue(e.message)
                }
            }
        }
    }

    private fun createPost(dogCreatorId: Long, image: URI, thumb: URI): Post {
        return Post(
            dogCreatorId = dogCreatorId,
            image = image.toString(),
            thumbnail = thumb.toString(),
            description = _descriptionPostLiveData.value!!
        )
    }

    private suspend fun saveImages(): Pair<URI, URI> {
        val image = fileHelper.saveFileIntoAppsDir(_imageLiveData.value!!, "post")
        val thumb = fileHelper.createThumbnail(image, "post_thumb")
        return Pair(image, thumb)
    }

    private fun validateImage(): Validation {
        return when (_imageLiveData.value) {
            null -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun validateDescription(): Validation {
        return when {
            _descriptionPostLiveData.value.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }
}