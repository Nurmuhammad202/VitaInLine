package uz.hayot.vitaInLine.ui.main

import android.content.ContentValues
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
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val success = MutableLiveData<Boolean>()
    private var userResponse = UserResponse()


      fun getSignUser() = viewModelScope.launch {
        try {
            repository.getUser(repository.getToken()).let {
                if (it.isSuccessful) {
                    it.body()?.let { body ->
                        userResponse = body
                        success.value = true
                    }
                }
                Log.e("salom", "getUser: $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    fun getUser(): UserResponse {
        return userResponse
    }


}