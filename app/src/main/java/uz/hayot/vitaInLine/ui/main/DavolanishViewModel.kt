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
import uz.hayot.vitaInLine.data.local.room.entity.PillModel
import uz.hayot.vitaInLine.data.local.room.entity.RecommendationModel
import uz.hayot.vitaInLine.data.model.DomainResponseEmploee
import uz.hayot.vitaInLine.data.model.HealingResponse
import uz.hayot.vitaInLine.util.network.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class DavolanishViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private lateinit var healingDate: List<PillModel>
    private lateinit var healingDateHistory: HealingResponse
    var success = MutableLiveData<Boolean>()
    private var errorText: String = ""


    fun getHealing() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {
                    Log.e("OSON", "recommendations123123: ${repository.getToken()}")
                    repository.getHealing().let {
                        if (it.isSuccessful) {
                            it.body()?.let { data ->
                                //internetdan kelgan datadan listni olish
                                val list = data.data!!
                                //room uchun cashe list
                                val casheList = ArrayList<PillModel>()

                                // internetdan kelgan listdan cashe list tayyorlash
                                for (listItem in list) {
                                    val timeList = ArrayList<String>()

                                    for (timeItem in listItem?.times!!) {
                                        timeList.add(timeItem)
                                    }
                                    val pillEntity = PillModel(
                                        listItem.id!!,
                                        listItem.period,
                                        listItem.quantity,
                                        listItem.endedDate,
                                        listItem.patientId,
                                        listItem.startedDate,
                                        listItem.type,
                                        listItem.pill,
                                        listItem.pillId,
                                        listItem.createdAt,
                                        timeList,
                                        listItem.doctorId,
                                        listItem.information,
                                        listItem.extraInformation,
                                        listItem.updatedAt,
                                        listItem.title
                                    )
                                    casheList.add(pillEntity)
                                }
                                healingDate = casheList

                                // saqlangan listni viewga berish

                                Log.d("casheList", healingDate.toString())

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
        } else {
            //   agarda internet bo'lmagan holat
            try {
                healingDate = getPillDataRoom()
                success.value = true
            } catch (e: Exception) {
                e.message
                Log.e("offlineError", "${e.message}")
            }

        }
    }


    fun getPillDataRoom(): List<PillModel> {
        return repository.getRoomPill()
    }

    fun savePillDataRoom(list: List<PillModel>) {
        repository.saveRoomPill(list)
    }


    fun getHealingHistory() = viewModelScope.launch {
        try {
            repository.getHealingHistory().let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        healingDateHistory = data
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

    private lateinit var recommendationsData: List<RecommendationModel>
    fun recommendations() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {
                    Log.e("OSON", "recommendations123123: ${repository.getToken()}")
                    repository.recommendations().let {
                        if (it.isSuccessful) {
                            it.body()?.let { data ->
                                //internetdan kelgan datadan listni olish
                                val list = data.data!!
                                //room uchun cashe list
                                val casheList = ArrayList<RecommendationModel>()

                                // internetdan kelgan listdan cashe list tayyorlash
                                for (listItem in list) {
                                    val timeList = ArrayList<String>()
                                    if (listItem?.times?.isNotEmpty() == true) {
                                        for (timeItem in listItem.times) {
                                            timeList.add(timeItem)
                                        }
                                    }

                                    val recommendationEntity = RecommendationModel(
                                        listItem?.id!!,
                                        listItem.period,
                                        listItem.quantity,
                                        listItem.endedDate,
                                        listItem.patientId,
                                        listItem.startedDate,
                                        listItem.type,
                                        listItem.pill,
                                        listItem.pillId,
                                        listItem.createdAt,
                                        timeList,
                                        listItem.doctorId,
                                        listItem.information,
                                        listItem.extraInformation,
                                        listItem.updatedAt,
                                        listItem.title
                                    )
                                    casheList.add(recommendationEntity)
                                }
                                recommendationsData = casheList

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
        } else {
            //   agarda internet bo'lmagan holat
            try {
                recommendationsData = getRecommendationDataRoom()
                success.value = true
            } catch (e: Exception) {
                e.message
                Log.e("offlineError", "${e.message}")
            }
        }
    }


    fun getRecommendationDataRoom(): List<RecommendationModel> {
        return repository.getRoomRecommendation()
    }

    fun saveRecommendationDataRoom(list: List<RecommendationModel>) {
        repository.saveRoomRecommendation(list)
    }


    private lateinit var recommendationsDataHistory: HealingResponse
    fun recommendationsHistory() = viewModelScope.launch {
        try {
            repository.recommendationsHistory().let {
                if (it.isSuccessful) {
                    it.body()?.let { data ->
                        recommendationsDataHistory = data
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


    fun getErrorText() = errorText.ifEmpty { "Information error" }
    fun getHealingData(): List<PillModel> = healingDate
    fun getHealingHistoryData() = healingDateHistory
    fun getRecommendationData() = recommendationsData
    fun getRecommendationHisData() = recommendationsDataHistory
}