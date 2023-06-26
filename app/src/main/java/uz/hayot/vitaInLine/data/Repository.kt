package uz.hayot.vitaInLine.data

import uz.hayot.vitaInLine.data.local.SharedInterface
import uz.hayot.vitaInLine.data.model.CreateDataPatient
import uz.hayot.vitaInLine.data.model.SendSigInModel
import uz.hayot.vitaInLine.data.remote.ApiInterface
import javax.inject.Inject

class Repository @Inject constructor(
    private val sharedInterface: SharedInterface,
    private val apiInterface: ApiInterface

) {

    fun getLang(): String = sharedInterface.getLang()

    fun getToken(): String = sharedInterface.getToken()

    fun saveLang(lang: String) = sharedInterface.saveLang(lang = lang)

    fun saveAlarm(date: Int) = sharedInterface.setAlarm( date)
    fun getAlarm(): Int = sharedInterface.getAlarm()

    suspend fun saveToken(token: String) = sharedInterface.saveToken(token = token)

    suspend fun createUser(data: CreateDataPatient) = apiInterface.createUser(data)

    suspend fun sigIn(sendSigInModel: SendSigInModel) = apiInterface.signIn(sendSigInModel)

    suspend fun getUser() = apiInterface.getUser(sharedInterface.getToken())

    suspend fun getHealing() =
        apiInterface.getHealing(authToken = sharedInterface.getToken(), type = "current")

    suspend fun getHealingHistory() =
        apiInterface.getHealing(authToken = sharedInterface.getToken(), type = "history")

    suspend fun recommendations() =
        apiInterface.recommendations(authToken = sharedInterface.getToken(), type = "current")

    suspend fun recommendationsHistory() =
        apiInterface.recommendations(authToken = sharedInterface.getToken(), type = "history")

    suspend fun advertising() = apiInterface.advertising()

}