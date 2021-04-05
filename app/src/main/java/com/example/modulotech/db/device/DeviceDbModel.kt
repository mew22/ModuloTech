package com.example.modulotech.db.device

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.modulotech.db.user.UserDbModel

sealed class DeviceDbModel {
    abstract val id: Int
    abstract val userId: Int
    abstract val name: String

    abstract fun getDeviceType(): String

    override fun equals(other: Any?): Boolean =
        if (other is DeviceDbModel &&
            other::class == this::class
        ) {
            id == other.id
        } else {
            false
        }

    fun equalsContent(other: Any?): Boolean =
        if (!equals(other)) {
            false
        } else {
            name == (other as DeviceDbModel).name
        }
}

// After Kotlin 1.5, sealed class can be extracted to others files
// For DAO, 1 or 3 same things for Device
// 1 for Device

@Entity(
    tableName = "Heater",
    indices = [Index("userId")],
    foreignKeys = [ForeignKey(
        entity = UserDbModel::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class HeaterDbModel(
    @PrimaryKey override val id: Int,
    override val userId: Int,
    override val name: String,
    val mode: DeviceMode,
    val temperature: Float
) : DeviceDbModel() {
    companion object {
        const val TABLE_NAME = "heater"
        const val DEVICE_TYPE = "Heater"
    }

    override fun getDeviceType(): String = DEVICE_TYPE
}

@Entity(
    tableName = "Light",
    indices = [Index("userId")],
    foreignKeys = [ForeignKey(
        entity = UserDbModel::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class LightDbModel(
    @PrimaryKey override val id: Int,
    override val userId: Int,
    override val name: String,
    val mode: DeviceMode,
    val intensity: Int
) : DeviceDbModel() {
    companion object {
        const val TABLE_NAME = "light"
        const val DEVICE_TYPE = "Light"
    }

    override fun getDeviceType(): String = DEVICE_TYPE
}

@Entity(
    tableName = "RollerShutter",
    indices = [Index("userId")],
    foreignKeys = [ForeignKey(
        entity = UserDbModel::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class RollerShutterDbModel(
    @PrimaryKey override val id: Int,
    override val userId: Int,
    override val name: String,
    val position: Int
) : DeviceDbModel() {
    companion object {
        const val TABLE_NAME = "rollershutter"
        const val DEVICE_TYPE = "RollerShutter"
    }

    override fun getDeviceType(): String = DEVICE_TYPE
}

sealed class DeviceMode {
    abstract fun getMode(): String

    object DeviceOn : DeviceMode() {
        const val DEVICE_MODE = "ON"
        override fun getMode(): String = DEVICE_MODE
    }

    object DeviceOff : DeviceMode() {
        const val DEVICE_MODE = "OFF"
        override fun getMode(): String = DEVICE_MODE
    }
}
