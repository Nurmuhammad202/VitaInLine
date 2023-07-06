package uz.hayot.vitaInLine.ui.main

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
import uz.hayot.vitaInLine.data.model.HealingResponse
import javax.inject.Inject

@HiltViewModel
class DavolanishViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private lateinit var healingDate:HealingResponse
    private lateinit var healingDateHistory:HealingResponse
    var success=MutableLiveData<Boolean>()
    private var errorText: String = ""


    fun getHealing() = viewModelScope.launch {
        try {
            repository.getHealing().let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        healingDate = data
                        success.value=true
                    }
                }else {
                    val gson = Gson()
                    val type = object : TypeToken<DomainResponseEmploee>() {}.type
                    val errorResponse: DomainResponseEmploee? =
                        gson.fromJson(it.errorBody()!!.charStream(), type)

                    errorResponse?.let { error ->
                        errorText = error.message
                    }
                    Log.e(
                        ContentValues.TAG, "getEmployee vs $errorResponse "
                    )
                    success.value = false
                }
                Log.e("token", repository.getToken())
                Log.e("healingData", "  $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
            Log.e("healingData1", "${ex.message}")
        }
    }

    fun getHealingHistory() = viewModelScope.launch {
        try {
            repository.getHealingHistory().let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        healingDateHistory = data
                        success.value=true
                    }
                }else {
                    val gson = Gson()
                    val type = object : TypeToken<DomainResponseEmploee>() {}.type
                    val errorResponse: DomainResponseEmploee? =
                        gson.fromJson(it.errorBody()!!.charStream(), type)

                    errorResponse?.let { error ->
                        errorText = error.message
                    }
                    Log.e(
                        ContentValues.TAG, "getEmployee vs $errorResponse "
                    )
                    success.value = false
                }
                Log.e("healingDatadsfdsfds", "  $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

   private lateinit var recommendationsData:HealingResponse
    fun recommendations() = viewModelScope.launch {
        try {
            repository.recommendations().let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        recommendationsData = data
                        success.value=true
                    }
                }else {
                    val gson = Gson()
                    val type = object : TypeToken<DomainResponseEmploee>() {}.type
                    val errorResponse: DomainResponseEmploee? =
                        gson.fromJson(it.errorBody()!!.charStream(), type)

                    errorResponse?.let { error ->
                        errorText = error.message
                    }
                    Log.e(
                        ContentValues.TAG, "getEmployee vs $errorResponse "
                    )
                    success.value = false
                }
                Log.e("recommendationsData", "  $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    private lateinit var recommendationsDataHistory:HealingResponse
    fun recommendationsHistory() = viewModelScope.launch {
        try {
            repository.recommendationsHistory().let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        recommendationsDataHistory = data
                        success.value=true
                    }
                }else {
                    val gson = Gson()
                    val type = object : TypeToken<DomainResponseEmploee>() {}.type
                    val errorResponse: DomainResponseEmploee? =
                        gson.fromJson(it.errorBody()!!.charStream(), type)

                    errorResponse?.let { error ->
                        errorText = error.message
                    }
                    Log.e(
                        ContentValues.TAG, "getEmployee vs $errorResponse "
                    )
                    success.value = false
                }
                Log.e("healingDatadsfdsfds", "  $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }


    fun saveAlarm(date: Int) = repository.saveAlarm(date)
    fun getAlarm(): Int = repository.getAlarm()
    fun getErrorText() = errorText.ifEmpty { "Information error" }
    fun getHealingData() = healingDate
    fun getHealingHistoryData() = healingDateHistory
    fun getRecommendationData() = recommendationsData
    fun getRecommendationHisData() = recommendationsDataHistory
}