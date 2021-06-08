package by.kleban.dogdairy.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.DogDiaryApplication
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Sex
import by.kleban.dogdairy.entities.Validation
import by.kleban.dogdairy.repositories.DogRepository
import by.kleban.dogdairy.repositories.DogRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI
import java.util.*


class RegistrationViewModel : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val repository: DogRepository = DogRepositoryImpl.getDogRepository(DogDiaryApplication.instance)

    private val _nameLiveData = MutableLiveData<String>()
    val nameLiveData: LiveData<String>
        get() = _nameLiveData

    private val _imageLiveData = MutableLiveData<String>()
    val imageLiveData: LiveData<String>
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

    fun saveImageFile(uri: Uri, context: Context) {
        ioScope.launch {
            val originalImgUri = URI(uri.toString())
            val originalImgUriAndroid = Uri.parse(originalImgUri.toString())
            val dataDir = ContextCompat.getDataDir(context) ?: throw java.lang.Exception()
            val newImgFile = File(dataDir.path, "avatar_${UUID.randomUUID()}")
            val outputStream = newImgFile.outputStream()
            val inputStream = context.contentResolver.openInputStream(originalImgUriAndroid) ?: throw java.lang.Exception()
            try {
                inputStream.copyTo(outputStream)
            } catch (e: Exception) {
                e.message?.let { Log.e(TAG, it) }
            } finally {
                outputStream.close()
                inputStream.close()
            }
            val newImgUri = newImgFile.toURI()
            _imageLiveData.postValue(newImgUri.toString())
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
            _imageLiveData.value.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun validateDescription(): Validation {
        return when {
            _descriptionLiveData.value.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private val _dogIdLiveData = MutableLiveData<Long>()
    val dogIdLiveData: LiveData<Long>
        get() = _dogIdLiveData

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
                    val dogId = repository.saveDog(createDog())
                    _dogIdLiveData.postValue(dogId)
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
            image = _imageLiveData.value!!,
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
