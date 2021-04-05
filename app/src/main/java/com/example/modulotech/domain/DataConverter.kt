package com.example.modulotech.domain

import com.example.modulotech.db.device.DeviceMode
import com.example.modulotech.db.device.HeaterDbModel
import com.example.modulotech.db.device.LightDbModel
import com.example.modulotech.db.device.RollerShutterDbModel
import com.example.modulotech.db.user.AddressDbModel
import com.example.modulotech.db.user.UserDbModel
import com.example.modulotech.service.model.AddressRemote
import com.example.modulotech.service.model.HeaterRemote
import com.example.modulotech.service.model.LightRemote
import com.example.modulotech.service.model.RollerShutterRemote
import com.example.modulotech.service.model.UserRemote

fun userRemoteToDbModel(userRemote: UserRemote) =
    UserDbModel(
        firstName = userRemote.firstName, lastName = userRemote.lastName,
        address = addressRemoteToDbModel(userRemote.address), birthDate = userRemote.birthDate
    )

fun addressRemoteToDbModel(addressRemote: AddressRemote) =
    AddressDbModel(
        city = addressRemote.city, postalCode = addressRemote.postalCode,
        street = addressRemote.street, streetCode = addressRemote.streetCode,
        country = addressRemote.country
    )

fun lightRemoteToDbModel(lightRemote: LightRemote, userId: Int) =
    LightDbModel(
        id = lightRemote.id,
        userId = userId,
        name = lightRemote.name,
        mode = getDeviceMode(lightRemote.mode),
        intensity = lightRemote.intensity
    )

fun heaterRemoteToDbModel(heaterRemote: HeaterRemote, userId: Int) =
    HeaterDbModel(
        id = heaterRemote.id,
        userId = userId,
        name = heaterRemote.name,
        mode = getDeviceMode(heaterRemote.mode),
        temperature = heaterRemote.temperature
    )

fun rollerShutterRemoteToDbModel(rollerShutterRemote: RollerShutterRemote, userId: Int) =
    RollerShutterDbModel(
        id = rollerShutterRemote.id, userId = userId,
        name = rollerShutterRemote.name, position = rollerShutterRemote.position
    )

fun getDeviceMode(mode: String): DeviceMode =
    DeviceMode::class.sealedSubclasses.firstOrNull {
        it.objectInstance?.getMode().equals(mode)
    }?.objectInstance ?: DeviceMode.DeviceOff
