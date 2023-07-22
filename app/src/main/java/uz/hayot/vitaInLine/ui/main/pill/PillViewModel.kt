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
import uz.hayot.vitaInLine.data.model.DataPill
import uz.hayot.vitaInLine.data.model.DataPillModel
import uz.hayot.vitaInLine.data.model.DomainResponseEmploee
import uz.hayot.vitaInLine.data.model.HealingResponse
import javax.inject.Inject

@HiltViewModel
class PillViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private lateinit var healingDate: HealingResponse
    val successHealing = MutableLiveData<Boolean>()
    val successPill = MutableLiveData<Boolean>()
    private val _pillDataList = MutableLiveData<List<DataPill>>()
    private var errorText: String = ""

    fun getHealing() = viewModelScope.launch {
        try {
            repository.getHealing().let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        healingDate = data
                        successHealing.value = true
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
                        ContentValues.TAG, "getEmployee vs $errorResponse "
                    )
                    successHealing.value = false
                }
                Log.e("token", repository.getToken())
                Log.e("healingData", "  $it vs ${it.body()}")
            }
        } catch (ex: Exception) {
            ex.message
            Log.e("healingData1", "${ex.message}")
        }
    }


    fun fetchPillData(list: MutableList<String>) = viewModelScope.launch {
        try {
            val dataList: MutableList<DataPill> = ArrayList()
            for (i in 0 until list.size) {
                repository.getPillById(list[i]).let { response ->
                    if (response.isSuccessful) {
                        dataList.add(response.body()?.data as DataPill)
                        successPill.value = false
                    }
                }
            }
            _pillDataList.value = dataList
            successPill.value = true
            Log.e(
                ContentValues.TAG, "getEmployee vs ${_pillDataList.value} "
            )
        } catch (Ex: Exception) {
            Ex.message
        }
    }





    fun getPillData(): List<DataPill>? = _pillDataList.value
    fun getErrorText() = errorText

    fun getHealingData() = healingDate
}