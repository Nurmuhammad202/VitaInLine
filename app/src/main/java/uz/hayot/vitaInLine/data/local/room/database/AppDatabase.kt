package io.geo.go.database.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.hayot.vitaInLine.data.local.room.dao.VitaDao
import io.geo.go.database.room.database.AppDatabase.Companion.DATABASE_VERSION
import uz.hayot.vitaInLine.data.local.room.entity.PillModel

@Database(
    version = DATABASE_VERSION,
    entities = [PillModel::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getVitaDAO(): VitaDao


    companion object {
        private var dbINSTANCE: AppDatabase? = null
        const val TABLE_PILL="pill"
        private const val DATABASE_NAME = "vita_db"
        const val DATABASE_VERSION = 1
        fun getDataDB(context: Context): AppDatabase {
            if (dbINSTANCE == null) {
                dbINSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return dbINSTANCE!!
        }
    }
}
