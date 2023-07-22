package uz.hayot.vitaInLine.data.local

interface SharedInterface {
    fun getToken(): String
    suspend fun saveToken(token: String)

    fun saveLang(lang: String)

    fun getLang(): String

    fun setAlarm(date: Int)
    fun getAlarm(): Int
}