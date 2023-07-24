package uz.hayot.vitaInLine.data

import uz.hayot.vitaInLine.data.local.room.dao.VitaDao
import uz.hayot.vitaInLine.data.local.room.entity.PillModel
import uz.hayot.vitaInLine.data.local.sharedPref.SharedInterface
import uz.hayot.vitaInLine.data.model.CreateDataPatient
import uz.hayot.vitaInLine.data.model.SendSigInModel
import uz.hayot.vitaInLine.data.model.doctorById.ApproveConfirmationModel
import uz.hayot.vitaInLine.data.remote.ApiInterface
import javax.inject.Inject

class Repository @Inject constructor(
    private val sharedInterface: SharedInterface,
    private val apiInterface: ApiInterface,
    private val vitaDao: VitaDao
) {

    fun getLang(): String = sharedInterface.getLang()

    fun getToken(): String = sharedInterface.getToken()

    fun saveLang(lang: String) = sharedInterface.saveLang(lang = lang)

    fun saveAlarm(date: Int) = sharedInterface.setAlarm(date)
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
    suspend fun getPillById(pillId: String) = apiInterface.getPillById(pillId)

    suspend fun getConfirmations() = apiInterface.getConfirmations(sharedInterface.getToken())

    suspend fun getDoctorById(doctorId: String) =
        apiInterface.getDoctorById(id = doctorId, sharedInterface.getToken())

    suspend fun confirmations(approveConfirmationModel: ApproveConfirmationModel) =
        apiInterface.confirmations(sharedInterface.getToken(), approveConfirmationModel)

    //save pill
    fun saveRoomPill(pillModel: PillModel) = vitaDao.insertPill(pillModel)

    fun getRoomPill() = vitaDao.getPill()

}