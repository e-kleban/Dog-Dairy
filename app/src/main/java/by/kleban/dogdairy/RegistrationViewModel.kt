package by.kleban.dogdairy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.registration.Registration
import by.kleban.dogdairy.validation.Validation


class RegistrationViewModel : ViewModel() {

    private val _nameLiveData = MutableLiveData<String>()
    val nameLiveData: LiveData<String>
        get() = _nameLiveData

    private val _imageLiveData = MutableLiveData<String>()
    val imageLiveData: LiveData<String>
        get() = _imageLiveData

    private val _ageLiveData = MutableLiveData<String>()
    val ageLiveData: LiveData<String>
        get() = _ageLiveData

    private val _descriptionLiveData = MutableLiveData<String>()
    val descriptionLiveData: LiveData<String>
        get() = _descriptionLiveData

    private val _sexLiveData = MutableLiveData<String>()
    val sexLiveData: LiveData<String>
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


    private val _registrationLiveData = MutableLiveData<Registration>()
    val registrationLiveData: LiveData<Registration>
        get() = _registrationLiveData

    fun saveName(name: String) {
        _nameLiveData.value = name
    }

    fun saveAge(age: String) {
        _ageLiveData.value = age
    }

    fun saveDescription(description: String) {
        _descriptionLiveData.value = description
    }

    fun saveImage(image: String) {
        _imageLiveData.value = image
    }

    fun saveSex(sex: String) {
        _sexLiveData.value = sex
    }

    fun saveBreed(breed: String) {
        _breedLiveData.value = breed
    }

    fun registration() {
        _validationNameLiveData.value = validateName()
        _validationAgeLiveData.value = validateAge()
        _validationImageLiveData.value = validateImage()
        _validationSexLiveData.value = validateSex()
        _validationBreedLiveData.value = validateBreed()
        _validationDescriptionLiveData.value = validateDescription()
        registerUser()
    }

    private fun validateName(): Validation {
        return when {
            _nameLiveData.value.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun validateAge(): Validation {
        return when {
            _ageLiveData.value.isNullOrEmpty() -> Validation.EMPTY
            else -> Validation.VALID
        }
    }

    private fun validateSex(): Validation {
        return when {
            _sexLiveData.value.isNullOrEmpty() -> Validation.EMPTY
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

    private fun registerUser() {
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
            _registrationLiveData.value = Registration.POSSIBLE
        } else {
            _registrationLiveData.value = Registration.IMPOSSIBLE
        }
    }

    companion object {
        private val TAG = RegistrationViewModel::class.java.simpleName
    }
}
