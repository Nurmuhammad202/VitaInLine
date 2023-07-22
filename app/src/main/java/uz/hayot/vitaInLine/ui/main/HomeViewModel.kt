package uz.hayot.vitaInLine.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.hayot.vitaInLine.data.Repository
import uz.hayot.vitaInLine.data.model.DomainResponseEmploee
import uz.hayot.vitaInLine.data.model.UserResponse
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private lateinit var userResponse:UserResponse
    val success = MutableLiveData<Boolean>()
    private var errorText: String = ""

    fun getSignUser() = viewModelScope.launch {
        try {
            Log.e(TAG, "getSignUser: ${repository.getToken()}")
            repository.getUser().let {
                if (it.isSuccessful) {
                    it.body()?.let { body ->
                        userResponse = body
                        success.value = true
                    }
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<DomainResponseEmploee>() {}.type
                    val errorResponse: DomainResponseEmploee? =
                        gson.fromJson(it.errorBody()!!.charStream(), type)

                    errorResponse?.let { error ->
                        errorText = error.message
                    }
                    Log.e(
                        TAG, "getEmployee vs $errorResponse "
                    )
                    success.value = false
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

    fun saveAlarm(date: Int) = repository.saveAlarm(date)
    fun getErrorText() = errorText.ifEmpty { "Home information error" }
    fun getHomeUserData() = userResponse

}