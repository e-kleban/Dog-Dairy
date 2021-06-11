package by.kleban.dogdairy.ui.registration

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Sex
import by.kleban.dogdairy.entities.Validation
import by.kleban.dogdairy.entities.file_helper.FileHelper
import by.kleban.dogdairy.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var fileHelper: FileHelper

    @Inject
    lateinit var repository: DogRepository

    private val _nameLiveData = MutableLiveData<String>()
    val nameLiveData: LiveData<String>
        get() = _nameLiveData

    private val _imageLiveData = MutableLiveData<Pair<String, String>>()
    val imageLiveData: LiveData<Pair<String, String>>
        get() = _imageLiveData

    private val _ageLiveData = MutableLiveData<Int>()
    val ageLiveData: LiveData<Int>
        get() = _ageLiveData

    private val _descriptionLiveData = MutableLiveData<String>()
    val descriptionLiveData: LiveData<String>
        get() = _descriptionLiveData

    private val _sexLiveData = MutableLiveData<Sex>()
    val sexLiveData: LiveData<Sex>
        get() = _sexLiveData

    private val _breedLiveData = MutableLiveData<String>()
    val breedLiveData: LiveData<String>
        get() = _breedLiveData

    private val _validationNameLiveData = MutableLiveData<Validation>()
    val validationNameLiveData: LiveData<Validation>
        get() = _validationNameLiveData

    private val _validationImageLiveData = MutableLiveData<Validation>()
    val validationImageLiveData: LiveData<Validation>
        get() = _validationImageLiveData

    private val _validationAgeLiveData = MutableLiveData<Validation>()
    val validationAgeLiveData: LiveData<Validation>
        get() = _validationAgeLiveData

    private val _validationDescriptionLiveData = MutableLiveData<Validation>()
    val validationDescriptionLiveData: LiveData<Validation>
        get() = _validationDescriptionLiveData

    private val _validationBreedLiveData = MutableLiveData<Validation>()
    val validationBreedLiveData: LiveData<Validation>
        get() = _validationBreedLiveData

    private val _validationSexLiveData = MutableLiveData<Validation>()
    val validationSexLiveData: LiveData<Validation>
        get() = _validationSexLiveData

    private val _registrationLiveData = MutableLiveData<Boolean>()
    val registrationLiveData: LiveData<Boolean>
        get() = _registrationLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = _isLoadingLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun saveName(name: String) {
        _nameLiveData.value = name
    }

    fun saveAge(age: Int) {
        _ageLiveData.value = age
    }

    fun saveDescription(description: String) {
        _descriptionLiveData.value = description
    }

    fun saveSex(sex: Sex) {
        _sexLiveData.value = sex
    }

    fun saveBreed(breed: String) {
        _breedLiveData.value = breed
    }

    fun saveImageFile(uri: Uri) {
        ioScope.launch {
            val image = fileHelper.saveFileIntoAppsDir(uri, "avatar")
            val thumb = fileHelper.createThumbnail(image, "avatar_thumb")
            _imageLiveData.postValue(Pair(image.toString(), thumb.toString()))
        }
    }

    fun registration() {
        _validationNameLiveData.value = validateName()
        _validationAgeLiveData.value = validateAge()
        _validationImageLiveData.value = validateImage()
        _validationSexLiveData.value = validateSex()
        _validationBreedLiveData.value = validateBreed()
        _validationDescriptionLiveData.value = validateDescription()
        registerDog()
    }

    private fun validateName(): Validation {
        return when {
            _nameLiveData.value.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun validateAge(): Validation {
        val age = _ageLiveData.value
        return when {
            age == null -> Validation.EMPTY
            age < 0 -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun validateSex(): Validation {
        return when (_sexLiveData.value) {
            null -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun validateBreed(): Validation {
        return when {
            _breedLiveData.value.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun validateImage(): Validation {
        return when {
            (_imageLiveData.value?.first.isNullOrEmpty() &&
                    _imageLiveData.value?.second.isNullOrEmpty()) -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun validateDescription(): Validation {
        return when {
            _descriptionLiveData.value.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun registerDog() {
        val validationName = _validationNameLiveData.value
        val validationAge = _validationAgeLiveData.value
        val validationImage = _validationImageLiveData.value
        val validationSex = _validationSexLiveData.value
        val validationBreed = _validationBreedLiveData.value
        val validationDescription = _validationDescriptionLiveData.value

        if (validationName == Validation.VALID &&
            validationAge == Validation.VALID &&
            validationSex == Validation.VALID &&
            validationImage == Validation.VALID &&
            validationBreed == Validation.VALID &&
            validationDescription == Validation.VALID
        ) {
            _isLoadingLiveData.value = true
            ioScope.launch {
                try {
                    repository.saveDog(createDog())
                    _registrationLiveData.postValue(true)
                    _isLoadingLiveData.postValue(false)
                } catch (e: Exception) {
                    _isLoadingLiveData.postValue(false)
                    _errorLiveData.postValue(e.message)
                }
            }
        } else {
            _registrationLiveData.value = false
        }
    }

    private fun createDog(): Dog {
        return Dog(
            name = _nameLiveData.value!!,
            bigImage = _imageLiveData.value!!.first,
            littleImage = _imageLiveData.value!!.second,
            age = _ageLiveData.value!!,
            sex = _sexLiveData.value!!,
            breed = _breedLiveData.value!!,
            description = _descriptionLiveData.value!!
        )
    }

    companion object {
        private val TAG = RegistrationViewModel::class.java.simpleName
    }
}
