package uz.hayot.vitaInLine.ui.main.pill

import android.content.ContentValues
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
import uz.hayot.vitaInLine.data.model.advertising.AdvertisingModel
import javax.inject.Inject

@HiltViewModel
class PillViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private lateinit var advertising:AdvertisingModel
    val success = MutableLiveData<Boolean>()
    private var errorText: String = ""

    fun pillViewModel() = viewModelScope.launch {
        try {
            repository.advertising().let { response ->
                if (response.isSuccessful) {
                    advertising= response.body()!!
                    success.value=true
                }
                else{
                    val gson = Gson()
                    val type = object : TypeToken<DomainResponseEmploee>() {}.type
                    val errorResponse: DomainResponseEmploee? =
                        gson.fromJson(response.errorBody()!!.charStream(), type)

                    errorResponse?.let { error ->
                        errorText = error.message
                    }
                    Log.e(
                        ContentValues.TAG, "getEmployee vs $errorResponse "
                    )
                    success.value = false
                }
            }
        } catch (Ex: Exception) {
            Ex.message
        }
    }


    fun getPillData() = advertising
    fun getErrorText()=errorText
}