package uz.hayot.vitaInLine.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.hayot.vitaInLine.data.Repository
import uz.hayot.vitaInLine.data.model.DataItem
import uz.hayot.vitaInLine.data.model.HealingResponse
import javax.inject.Inject

@HiltViewModel
class DavolanishViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val healingDate = MutableLiveData<HealingResponse>()
    var healingDateHistory =MutableLiveData<HealingResponse>()


    fun getHealing() = viewModelScope.launch {
        try {
            repository.getHealing().let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->

                        healingDate.value= data
                    }
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
                        healingDateHistory.value = data
                    }
                }
                Log.e("healingDatadsfdsfds", "  $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    var recommendationsData = MutableLiveData<HealingResponse>()
    fun recommendations() = viewModelScope.launch {
        try {
            repository.recommendations().let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        recommendationsData.value = data
                    }
                }
                Log.e("recommendationsData", "  $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    var recommendationsDataHistory = MutableLiveData<HealingResponse>()
    fun recommendationsHistory() = viewModelScope.launch {
        try {
            repository.recommendationsHistory().let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        recommendationsDataHistory.value = data
                    }
                }
                Log.e("healingDatadsfdsfds", "  $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }


    fun saveAlarm(date:Int) = repository.saveAlarm(date)
    fun getAlarm(): Int = repository.getAlarm()

}