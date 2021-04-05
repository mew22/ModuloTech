package com.example.modulotech.db.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // If a conflict occurs (like fetch from remote data) ignore the new data and keep the local
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: UserDbModel): Long

    // If a conflict occurs (like fetch from remote data) ignore the new data and keep the local
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entities: List<UserDbModel>): LongArray

    @Update
    fun update(entity: UserDbModel)

    @Update
    fun update(entities: List<UserDbModel>)

    @Delete
    fun delete(entity: UserDbModel)

    @Delete
    fun delete(entities: List<UserDbModel>)

    @Query("SELECT * FROM ${UserDbModel.TABLE_NAME} LIMIT 1")
    fun getFirstUserSync(): UserDbModel?

    @Query("SELECT * FROM ${UserDbModel.TABLE_NAME}")
    fun getUserSync(): List<UserDbModel>?

    @Query("SELECT * FROM ${UserDbModel.TABLE_NAME} WHERE :id LIKE userId")
    fun getUserByIdSync(id: Int): UserDbModel?

    @Query("SELECT * FROM ${UserDbModel.TABLE_NAME} LIMIT 1")
    fun getFirstUser(): Flow<UserDbModel?>

    @Query("SELECT * FROM ${UserDbModel.TABLE_NAME}")
    fun getUser(): Flow<List<UserDbModel>?>

    @Query("SELECT * FROM ${UserDbModel.TABLE_NAME} WHERE :id LIKE userId")
    fun getUserById(id: Int): Flow<UserDbModel?>
}
