package uz.hayot.vitaInLine.ui.auth

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
import uz.hayot.vitaInLine.data.model.CreateDataPatient
import uz.hayot.vitaInLine.data.model.DomainResponseEmploee
import uz.hayot.vitaInLine.data.model.SendSigInModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val success = MutableLiveData<Boolean>()
    private var errorText = ""


    fun createUser(dataPatient: CreateDataPatient) = viewModelScope.launch {
        try {
            Log.e(TAG, "createUser111: $dataPatient")
            repository.createUser(dataPatient).let {
                if (it.isSuccessful) {
                    it.body()?.let {
                        success.value = true
//                        signIn(SendSigInModel(body.data.birthday, body.data.passport))
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
                Log.e(TAG, "createUser: $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    fun getToken() = repository.getToken().isNotEmpty()


    fun signIn(sendSigInModel: SendSigInModel) = viewModelScope.launch {
        try {
            Log.e(TAG, "signIn: $sendSigInModel")
            repository.sigIn(sendSigInModel).let {
                if (it.isSuccessful) {
                    it.body()?.let { body ->
                        repository.saveToken("Bearer ${body.token}")
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

                Log.e(TAG, "signIn: $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
            Log.e(
                TAG, "eExp: vs ${ex.message} "
            )
        }
    }

    fun getErrorText() = errorText.ifEmpty { "Authorization information error" }
}