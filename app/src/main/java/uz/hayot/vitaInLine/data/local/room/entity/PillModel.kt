package uz.hayot.vitaInLine.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.geo.go.database.room.database.AppDatabase

@Entity(tableName = AppDatabase.TABLE_PILL)
data class PillModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var time: Long,
    var driverName: String,
)
