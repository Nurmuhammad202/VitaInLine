package uz.hayot.vitaInLine.ui.main.doctorsDustup

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.hayot.vitaInLine.data.Repository
import uz.hayot.vitaInLine.data.model.DomainResponseEmploee
import uz.hayot.vitaInLine.data.model.doctorById.ApproveConfirmationModel
import uz.hayot.vitaInLine.data.model.doctorById.CustomDoctorById
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val customDoctorByIdModel = MutableLiveData<ArrayList<CustomDoctorById>>()
    private var errorText: String = ""
    fun getConfirmation() = viewModelScope.launch {
        val customDoctorByIdModelLocal = ArrayList<CustomDoctorById>()
        try {
            repository.getConfirmations().let {
                if (it.isSuccessful) {
                    it.body()?.let { getConfirmationModel ->
                        getConfirmationModel.data.forEach { data ->
                            val call3 = async { getDoctorById(data.doctorId) }
                            val customDoctor: CustomDoctorById = call3.await()
                            customDoctor.confirmationId = data.id
                            customDoctorByIdModelLocal.add(customDoctor)
                        }
                        customDoctorByIdModel.value = customDoctorByIdModelLocal
                        Log.e(TAG, "getConfirmationDocotrs: $customDoctorByIdModelLocal")
                    }

                } else {
                    val gson = Gson()
                    val type = object : TypeToken<DomainResponseEmploee>() {}.type
                    val errorResponse: DomainResponseEmploee? =
                        gson.fromJson(it.errorBody()!!.charStream(), type)

                    errorResponse?.let { error ->
                        errorText = error.message
                    }
                }
                Log.e(TAG, "getConfirmation: $it")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    private suspend fun getDoctorById(id: String): CustomDoctorById {
        var userName = ""
        var specialty = ""
        val confirmationId = ""
        try {
            repository.getDoctorById(id).let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        userName = data.data.fullname
                        specialty = data.data.specialty
                    }
                }
            }
        } catch (ex: Exception) {
            ex.message
        }

        return CustomDoctorById(userName, specialty, confirmationId)
    }

    val successListener = MutableLiveData<Boolean>()
    fun confirmations(id: String) = viewModelScope.launch {
        try {
            repository.confirmations(ApproveConfirmationModel(id)).let {
                it.body()?.let {
                    successListener.value = true
                }
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    fun getErrorText() = errorText.ifEmpty { "Information error" }
}