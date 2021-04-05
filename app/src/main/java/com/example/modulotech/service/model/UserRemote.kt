package com.example.modulotech.service.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserRemote(
    @field:SerializedName("firstName") val firstName: String,
    @field:SerializedName("lastName") val lastName: String,
    @field:SerializedName("address") val address: AddressRemote,
    @field:SerializedName("birthDate") val birthDate: Date
)

data class AddressRemote(
    @field:SerializedName("city") val city: String,
    @field:SerializedName("postalCode") val postalCode: Int,
    @field:SerializedName("street") val street: String,
    @field:SerializedName("streetCode") val streetCode: String,
    @field:SerializedName("country") val country: String
)
