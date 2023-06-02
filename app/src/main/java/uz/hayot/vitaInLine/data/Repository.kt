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

    suspend fun getToken(): String = sharedInterface.getToken()

    suspend fun saveLang(lang: String) = sharedInterface.saveLang(lang = lang)

    suspend fun saveToken(token: String) = sharedInterface.saveToken(token = token)

    suspend fun createUser(data: CreateDataPatient) = apiInterface.createUser(data)

    suspend fun sigIn(sendSigInModel: SendSigInModel) = apiInterface.signIn(sendSigInModel)
}