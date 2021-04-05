package com.example.modulotech.db.device

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    // Light

    // If a conflict occurs (like fetch from remote data) ignore the new data and keep the local
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLight(entity: LightDbModel): Long

    // If a conflict occurs (like fetch from remote data) ignore the new data and keep the local
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLights(entities: List<LightDbModel>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLight(entity: LightDbModel)

    @Delete
    fun deleteLight(entity: LightDbModel)

    @Delete
    fun deleteLights(entities: List<LightDbModel>)

    @Query("SELECT * from ${LightDbModel.TABLE_NAME} WHERE :id LIKE id")
    fun getLightByIdSync(id: Int): LightDbModel?

    @Query("SELECT * from ${LightDbModel.TABLE_NAME} WHERE :id LIKE id")
    fun getLightById(id: Int): Flow<LightDbModel?>

    @Query("SELECT * from ${LightDbModel.TABLE_NAME}")
    fun getLights(): Flow<List<LightDbModel>?>

    // Heater
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHeater(entity: HeaterDbModel): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHeaters(entities: List<HeaterDbModel>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateHeater(entity: HeaterDbModel)

    @Delete
    fun deleteHeater(entity: HeaterDbModel)

    @Delete
    fun deleteHeaters(entities: List<HeaterDbModel>)

    @Query("SELECT * from ${HeaterDbModel.TABLE_NAME} WHERE :id LIKE id")
    fun getHeaterByIdSync(id: Int): HeaterDbModel?

    @Query("SELECT * from ${HeaterDbModel.TABLE_NAME} WHERE :id LIKE id")
    fun getHeaterById(id: Int): Flow<HeaterDbModel?>

    @Query("SELECT * from ${HeaterDbModel.TABLE_NAME}")
    fun getHeaters(): Flow<List<HeaterDbModel>?>

    // RollerShutter
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertShutter(entity: RollerShutterDbModel): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertShutters(entities: List<RollerShutterDbModel>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateShutter(entity: RollerShutterDbModel)

    @Delete
    fun deleteShutter(entity: RollerShutterDbModel)

    @Delete
    fun deleteShutters(entities: List<RollerShutterDbModel>)

    @Query("SELECT * from ${RollerShutterDbModel.TABLE_NAME} WHERE :id LIKE id")
    fun getShutterByIdSync(id: Int): RollerShutterDbModel?

    @Query("SELECT * from ${RollerShutterDbModel.TABLE_NAME} WHERE :id LIKE id")
    fun getShutterById(id: Int): Flow<RollerShutterDbModel?>

    @Query("SELECT * from ${RollerShutterDbModel.TABLE_NAME}")
    fun getShutters(): Flow<List<RollerShutterDbModel>?>
}
