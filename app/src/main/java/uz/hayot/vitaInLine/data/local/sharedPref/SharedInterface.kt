package uz.hayot.vitaInLine.data.local.sharedPref

interface SharedInterface {
    fun getToken(): String
    suspend fun saveToken(token: String)

    fun saveLang(lang: String)

    fun getLang(): String


}