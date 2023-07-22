package uz.hayot.vitaInLine.data.local

import android.content.Context

private const val SHARED_PREF_NAME = "SHARED_PREF_NAME"
private const val SHARED_USER_TOKEN = "SHARED_USER_TOKEN"
private const val SHARED_LANGUAGE = "SHARED_LANGUAGE"
private const val SHARED_ALARM = "SHARED_ALARM"

class SharedPrefRepository(context: Context) : SharedInterface {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    override fun getToken(): String {
        return sharedPreferences.getString(SHARED_USER_TOKEN, "") ?: ""
    }

    override suspend fun saveToken(token: String) {
        sharedPreferences.edit().putString(SHARED_USER_TOKEN, token).apply()
    }

    override fun saveLang(lang: String) {
        sharedPreferences.edit().putString(SHARED_LANGUAGE, lang).apply()
    }

    override fun getLang(): String {
        return sharedPreferences.getString(SHARED_LANGUAGE, "ru") ?: "ru"
    }

    override fun setAlarm(date: Int) {

        sharedPreferences.edit().putInt(SHARED_ALARM, date).apply()
    }

    override fun getAlarm(): Int {
        return sharedPreferences.getInt(SHARED_ALARM, 0)
    }


}