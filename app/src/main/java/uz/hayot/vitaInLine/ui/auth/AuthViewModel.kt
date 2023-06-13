package uz.hayot.vitaInLine.ui.auth

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.hayot.vitaInLine.data.Repository
import uz.hayot.vitaInLine.data.model.CreateDataPatient
import uz.hayot.vitaInLine.data.model.SendSigInModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun createUser(dataPatient: CreateDataPatient) = viewModelScope.launch {
        try {
            Log.e(TAG, "createUser111: $dataPatient")
            repository.createUser(dataPatient).let {
                if (it.isSuccessful) {
                    it.body()?.let { body ->
                        signIn(SendSigInModel(body.data.birthday, body.data.passport))
                    }
                }
                Log.e(TAG, "createUser: $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    fun getToken() = repository.getToken().isNotEmpty()

    val success = MutableLiveData<Boolean>()
    fun signIn(sendSigInModel: SendSigInModel) = viewModelScope.launch {
        try {
            Log.e(TAG, "signIn: $sendSigInModel")
            repository.sigIn(sendSigInModel).let {
                if (it.isSuccessful) {
                    it.body()?.let { body ->
                        repository.saveToken("Bearer ${body.token}")
                        success.value = true
                    }
                }
                Log.e(TAG, "signIn: $it vs ${it.body()}")

            }
        } catch (ex: Exception) {
            ex.message
        }
    }
}