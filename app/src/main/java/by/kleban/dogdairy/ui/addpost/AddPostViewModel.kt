package by.kleban.dogdairy.ui.addpost

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.entities.Post
import by.kleban.dogdairy.entities.Validation
import by.kleban.dogdairy.entities.file_helper.FileHelper
import by.kleban.dogdairy.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor() : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var fileHelper: FileHelper

    @Inject
    lateinit var repository: DogRepository

    private val _descriptionPostLiveData = MutableLiveData<String>()
    val descriptionPostLiveData: LiveData<String>
        get() = _descriptionPostLiveData

    private val _imagePostLiveData = MutableLiveData<String>()
    val imagePostLiveData: LiveData<String>
        get() = _imagePostLiveData

    private val _validationImageLiveData = MutableLiveData<Validation>()
    val validationImageLiveData: LiveData<Validation>
        get() = _validationImageLiveData

    private val _validationDescriptionLiveData = MutableLiveData<Validation>()
    val validationDescriptionLiveData: LiveData<Validation>
        get() = _validationDescriptionLiveData

    private val _isSavedPostLiveData = MutableLiveData<Boolean>()
    val isSavedPostLiveData: LiveData<Boolean>
        get() = _isSavedPostLiveData

    private val _newPostLiveData = MutableLiveData<Post>()
    val newPostLiveData: LiveData<Post>
        get() = _newPostLiveData

    fun saveDescription(description: String) {
        _descriptionPostLiveData.value = description
    }

    fun savePostImageFile(uri: Uri) {
        ioScope.launch {
            val newImgUri = fileHelper.saveFileIntoAppsDir(uri, "post")
            _imagePostLiveData.postValue(newImgUri.toString())
        }
    }

    fun addPost(dogCreatorId: Long) {
        val validateImage = validateImage()
        val validateDescription = validateDescription()
        _validationImageLiveData.value = validateImage
        _validationDescriptionLiveData.value = validateDescription
        if (validateImage == Validation.VALID &&
            validateDescription == Validation.VALID
        ) {
            ioScope.launch {
                val post = createPost(dogCreatorId)
                _newPostLiveData.postValue(post)
                repository.savePost(post)
                _isSavedPostLiveData.postValue(true)
            }
        }
    }

    private fun createPost(dogCreatorId: Long): Post {
        return Post(
            dogCreatorId = dogCreatorId,
            postImage = _imagePostLiveData.value!!,
            postDescription = _descriptionPostLiveData.value!!
        )
    }

    private fun validateImage(): Validation {
        return when {
            _imagePostLiveData.value.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun validateDescription(): Validation {
        return when {
            _descriptionPostLiveData.value.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    companion object {
        private val TAG = AddPostViewModel::class.java.simpleName
    }
}