package uz.hayot.vitaInLine.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.hayot.vitaInLine.data.Repository
import uz.hayot.vitaInLine.data.model.HealingResponse
import uz.hayot.vitaInLine.data.model.HealingType
import javax.inject.Inject

@HiltViewModel
class DavolanishViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val success = MutableLiveData<Boolean>()
    private var healingDate = HealingResponse()
    private lateinit var type: HealingType

    // body uchun type berish
    fun setType(type: String) {
        this.type = HealingType(type)
    }

    fun getHealing() = viewModelScope.launch {
        try {
            repository.getHealing(repository.getToken(), type).let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        healingDate = data
                        success.value = true
                    }
                }

                Log.e("type", type.type.toString())
                Log.e("token", repository.getToken())
                Log.e("healingData", "  $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
        }
    }

    fun getHealingData(): HealingResponse {
        return healingDate
    }
}