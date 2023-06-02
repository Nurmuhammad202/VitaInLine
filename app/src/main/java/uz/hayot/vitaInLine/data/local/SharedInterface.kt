package uz.hayot.vitaInLine.data.local

interface SharedInterface {
    suspend fun getToken(): String

    suspend fun saveToken(token: String)

    suspend fun saveLang(lang: String)

    fun getLang(): String
}