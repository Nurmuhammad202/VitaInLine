package uz.hayot.vitaInLine.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.geo.go.database.room.database.AppDatabase

@Entity(tableName = AppDatabase.TABLE_PILL)
data class PillModel(
    @PrimaryKey
    val id: String,
    val period: Int? = null,
    val quantity: Int? = null,
    val endedDate: String? = null,
    val patientId: String? = null,
    val startedDate: String? = null,
    val type: String? = null,
    val pill: String? = null,
    val pillId: String? = null,
    val createdAt: String? = null,
    val times: List<String>? = null,
    val doctorId: String? = null,
    val information: String? = null,
    val updatedAt: String? = null,
    val title: String? = null
)

class TimeTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }
}

