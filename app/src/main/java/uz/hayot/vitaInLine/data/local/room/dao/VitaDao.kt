package uz.hayot.vitaInLine.data.local.room.dao

import androidx.room.*
import io.geo.go.database.room.database.AppDatabase
import uz.hayot.vitaInLine.data.local.room.entity.PillModel
import uz.hayot.vitaInLine.data.local.room.entity.RecommendationModel

@Dao
interface VitaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPill(addresses: List<PillModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPill(addresses: PillModel): Long

    @Query("SELECT * FROM ${AppDatabase.TABLE_PILL}")
    fun getPill(): List<PillModel>

    @Query("DELETE FROM ${AppDatabase.TABLE_PILL}")
    fun deleteAllPill(): Int

    @Query("UPDATE ${AppDatabase.TABLE_PILL} SET id = :position WHERE id = :id")
    fun updatePill(id: Int, position: Long)

    @Update
    fun updatePill(addresses: List<PillModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecommendation(addresses: List<RecommendationModel>)

    @Query("SELECT * FROM ${AppDatabase.TABLE_RECOMMENDATION}")
    fun getRecommendation(): List<RecommendationModel>

    @Query("DELETE FROM ${AppDatabase.TABLE_RECOMMENDATION}")
    fun deleteAllRecommendation(): Int
}