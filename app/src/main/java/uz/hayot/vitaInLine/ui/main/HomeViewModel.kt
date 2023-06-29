package uz.hayot.vitaInLine.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.hayot.vitaInLine.data.Repository
import uz.hayot.vitaInLine.data.model.UserResponse
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var userResponse = MutableLiveData<UserResponse>()

    fun getSignUser() = viewModelScope.launch {
        try {
            Log.e(TAG, "getSignUser: ${repository.getToken()}")
            repository.getUser().let {
                if (it.isSuccessful) {
                    it.body()?.let { body ->
                        userResponse.value = body
                    }
                }
                Log.e("homeData", "getUser: $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    fun saveLang(lang: String) = repository.saveLang(lang)

    fun getLang() = repository.getLang()

    //Patient log out qilish
    fun logOutPatient() = viewModelScope.launch {
        repository.saveToken("")
    }

    fun saveAlarm(date:Int) = repository.saveAlarm(date)
}