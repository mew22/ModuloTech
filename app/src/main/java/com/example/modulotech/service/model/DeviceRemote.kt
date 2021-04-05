package com.example.modulotech.service.model

import com.google.gson.annotations.SerializedName

sealed class DeviceRemote {
    abstract val id: Int
    abstract val name: String
    abstract val type: String
}

// After Kotlin 1.5, sealed class can be extracted to others files

data class HeaterRemote(
    @field:SerializedName("id") override val id: Int,
    @field:SerializedName("deviceName") override val name: String,
    @field:SerializedName("productType") override val type: String,
    @field:SerializedName("mode") val mode: String,
    @field:SerializedName("temperature") val temperature: Float
) : DeviceRemote() {
    companion object {
        const val KEY_PARSER = "Heater"
    }
}

data class LightRemote(
    @field:SerializedName("id") override val id: Int,
    @field:SerializedName("deviceName") override val name: String,
    @field:SerializedName("productType") override val type: String,
    @field:SerializedName("mode") val mode: String,
    @field:SerializedName("intensity") val intensity: Int
) : DeviceRemote() {
    companion object {
        const val KEY_PARSER = "Light"
    }
}

data class RollerShutterRemote(
    @field:SerializedName("id") override val id: Int,
    @field:SerializedName("deviceName") override val name: String,
    @field:SerializedName("productType") override val type: String,
    @field:SerializedName("position") val position: Int
) : DeviceRemote() {
    companion object {
        const val KEY_PARSER = "RollerShutter"
    }
}
