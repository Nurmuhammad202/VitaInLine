package io.geo.go.database.room.database

import android.content.Context
import androidx.room.*
import io.geo.go.database.room.database.AppDatabase.Companion.DATABASE_VERSION
import uz.hayot.vitaInLine.data.local.room.dao.VitaDao
import uz.hayot.vitaInLine.data.local.room.entity.PillModel
import uz.hayot.vitaInLine.data.local.room.entity.RecommendationModel
import uz.hayot.vitaInLine.data.local.room.entity.TimeTypeConverter

@Database(
    entities = [PillModel::class, RecommendationModel::class],
    version = DATABASE_VERSION,
    exportSchema = false

)
@TypeConverters(TimeTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getVitaDAO(): VitaDao

    companion object {
        private var dbINSTANCE: AppDatabase? = null
        const val TABLE_PILL = "pill"
        const val TABLE_RECOMMENDATION = "recommendation"
        private const val DATABASE_NAME = "vita_pill_db"
        const val DATABASE_VERSION = 3
        fun getDataDB(context: Context): AppDatabase {
            if (dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .build()
            }
            return dbINSTANCE!!
        }
    }
}
