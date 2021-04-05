package com.example.modulotech.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.modulotech.db.DeviceDatabase.Companion.DATABASE_VERSION
import com.example.modulotech.db.device.DeviceDao
import com.example.modulotech.db.device.DeviceMode
import com.example.modulotech.db.device.HeaterDbModel
import com.example.modulotech.db.device.LightDbModel
import com.example.modulotech.db.device.RollerShutterDbModel
import com.example.modulotech.db.user.UserDao
import com.example.modulotech.db.user.UserDbModel
import com.example.modulotech.domain.getDeviceMode
import java.util.Date

/**
 * Database to store all ski resorts
 */
@Database(
    entities = [UserDbModel::class, LightDbModel::class,
        HeaterDbModel::class, RollerShutterDbModel::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(DateConverter::class, DeviceModeConverter::class)
abstract class DeviceDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun deviceDao(): DeviceDao

    companion object {
        private const val DATABASE_NAME = "device.db"
        const val DATABASE_VERSION = 1

        @Volatile
        private var INSTANCE: DeviceDatabase? = null

        fun getInstance(context: Context): DeviceDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DeviceDatabase::class.java, DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? = if (timestamp == null) null else Date(timestamp)

    @TypeConverter
    fun toTimestamp(date: Date?): Long? = date?.time
}

class DeviceModeConverter {
    @TypeConverter
    fun toDeviceMode(mode: String?): DeviceMode? = mode?.let { getDeviceMode(it) }

    @TypeConverter
    fun toString(mode: DeviceMode?): String? =
        mode?.getMode()
}
