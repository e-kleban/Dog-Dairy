package by.kleban.dogdairy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kleban.dogdairy.DogDiaryApplication
import by.kleban.dogdairy.entities.Dog
import by.kleban.dogdairy.entities.Registration
import by.kleban.dogdairy.entities.Validation
import by.kleban.dogdairy.repositories.DogRepository
import by.kleban.dogdairy.repositories.DogRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegistrationViewModel : ViewModel() {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val repository: DogRepository = DogRepositoryImpl.getDogBreedRepository(DogDiaryApplication.instance)

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

    fun saveAge(age: Int) {
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
            _registrationLiveData.value = Registration.POSSIBLE
            ioScope.launch { repository.saveDog(createDog()) }
        } else {
            _registrationLiveData.value = Registration.IMPOSSIBLE
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
