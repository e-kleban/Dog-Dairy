package by.kleban.dogdairy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


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

    private val _sexLiveData = MutableLiveData<Int>()
    val sexLiveData: LiveData<Int>
        get() = _sexLiveData


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

    fun saveSex(sexId: Int) {
        _sexLiveData.value = sexId
    }

    companion object {
        private val TAG = RegistrationViewModel::class.java.simpleName
    }
}
