package by.kleban.dogdairy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    val validationNameAgeData: LiveData<Validation>
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

    fun saveBreed(breed:String){
        _breedLiveData.value=breed
    }

    companion object {
        private val TAG = RegistrationViewModel::class.java.simpleName
    }
}
