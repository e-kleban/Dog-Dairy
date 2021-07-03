package by.kleban.dogdiary.ui.registration

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdiary.helper.FileHelper
import by.kleban.dogdiary.entities.Dog
import by.kleban.dogdiary.entities.Sex
import by.kleban.dogdiary.entities.Validation
import by.kleban.dogdiary.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val fileHelper: FileHelper,
    private val repository: DogRepository
) : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val _imageLiveData = MutableLiveData<Uri>()
    val imageLiveData: LiveData<Uri>
        get() = _imageLiveData

    private val _nameLiveData = MutableLiveData<String>()
    private val _ageLiveData = MutableLiveData<Int>()
    private val _descriptionLiveData = MutableLiveData<String>()
    private val _sexLiveData = MutableLiveData<Sex>()
    private val _breedLiveData = MutableLiveData<String>()

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

    fun chooseImage(uri: Uri) {
        _imageLiveData.value = uri
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
        return when (_imageLiveData.value) {
            null -> Validation.EMPTY
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
                    val (image, thumb) = saveImageFiles()
                    val dog = createDog(image, thumb)
                    repository.saveDog(dog)
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

    private suspend fun saveImageFiles(): Pair<URI, URI> {
        val image = fileHelper.saveFileIntoAppsDir(_imageLiveData.value!!, "avatar")
        val thumb = fileHelper.createThumbnail(image, "avatar_thumb")
        return Pair(image, thumb)
    }

    private fun createDog(image: URI, thumb: URI): Dog {
        return Dog(
            name = _nameLiveData.value!!,
            image = image.toString(),
            thumbnail = thumb.toString(),
            age = _ageLiveData.value!!,
            sex = _sexLiveData.value!!,
            breed = _breedLiveData.value!!,
            description = _descriptionLiveData.value!!
        )
    }
}
