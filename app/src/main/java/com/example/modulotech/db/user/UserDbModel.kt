package com.example.modulotech.db.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = UserDbModel.TABLE_NAME)
data class UserDbModel(
    @PrimaryKey val userId: Int = DEFAULT_ID, // No auto increment needed
    val firstName: String,
    val lastName: String,
    @Embedded val address: AddressDbModel,
    val birthDate: Date
) {
    companion object {
        const val TABLE_NAME = "user"
        const val DEFAULT_ID = 1
    }
}

@Entity
data class AddressDbModel(
    val city: String,
    val postalCode: Int,
    val street: String,
    val streetCode: String,
    val country: String
)
