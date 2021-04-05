package com.example.modulotech.service.model

import com.google.gson.annotations.SerializedName

data class UserWithDevicesRemote(
    @field:SerializedName("devices") val devices: List<DeviceRemote>?,
    @field:SerializedName("user") val user: UserRemote?
)
