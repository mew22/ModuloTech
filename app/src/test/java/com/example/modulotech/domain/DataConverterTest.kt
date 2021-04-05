package com.example.modulotech.domain

import com.example.modulotech.service.model.AddressRemote
import com.example.modulotech.service.model.HeaterRemote
import com.example.modulotech.service.model.LightRemote
import com.example.modulotech.service.model.RollerShutterRemote
import com.example.modulotech.service.model.UserRemote
import java.time.Instant
import java.util.Date
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class DataConverterTest {

    @Nested
    inner class UserRemoteToDbModel {
        @Test
        fun `create UserDbModel from UserRemote`() {
            val userRemote = UserRemote(
                firstName = "John", lastName = "Doe", birthDate = Date.from(Instant.now()),
                address = AddressRemote(
                    city = "Paris", postalCode = 75000,
                    street = "Rue Montbrun", streetCode = "36", country = "France"
                )
            )

            val userDb = userRemoteToDbModel(userRemote)

            assertEquals(userRemote.firstName, userDb.firstName)
            assertEquals(userRemote.lastName, userDb.lastName)
            assertEquals(userRemote.birthDate, userDb.birthDate)
            assertEquals(userRemote.address.city, userDb.address.city)
            assertEquals(userRemote.address.country, userDb.address.country)
            assertEquals(userRemote.address.postalCode, userDb.address.postalCode)
            assertEquals(userRemote.address.street, userDb.address.street)
            assertEquals(userRemote.address.streetCode, userDb.address.streetCode)
        }
    }

    @Nested
    inner class AddressRemoteToDbModel {
        @Test
        fun `create AddressDbModel from AddressRemote`() {
            val addressRemote =
                AddressRemote(
                    city = "Paris", postalCode = 75000,
                    street = "Rue Montbrun", streetCode = "36", country = "France"
                )

            val addressDb = addressRemoteToDbModel(addressRemote)

            assertEquals(addressRemote.city, addressDb.city)
            assertEquals(addressRemote.country, addressDb.country)
            assertEquals(addressRemote.postalCode, addressDb.postalCode)
            assertEquals(addressRemote.street, addressDb.street)
            assertEquals(addressRemote.streetCode, addressDb.streetCode)
        }
    }

    @Nested
    inner class LightRemoteToDbModel {
        @Test
        fun `create LightDbModel from LightRemote`() {
            val lightRemote =
                LightRemote(
                    id = 1, name = "MyLight", type = LightRemote.KEY_PARSER, mode = "ON",
                    intensity = 10
                )
            val userId = 1

            val lightDb = lightRemoteToDbModel(lightRemote, userId)

            assertEquals(userId, lightDb.userId)
            assertEquals(lightRemote.intensity, lightDb.intensity)
            assertEquals(lightRemote.mode, lightDb.mode.getMode())
            assertEquals(lightRemote.id, lightDb.id)
            assertEquals(lightRemote.name, lightDb.name)
        }
    }

    @Nested
    inner class HeaterRemoteToDbModel {
        @Test
        fun `create HeaterDbModel from HeaterRemote`() {
            val heaterRemote =
                HeaterRemote(
                    id = 1, name = "MyHeater", type = HeaterRemote.KEY_PARSER, mode = "ON",
                    temperature = 10f
                )
            val userId = 1

            val heaterDb = heaterRemoteToDbModel(heaterRemote, userId)

            assertEquals(userId, heaterDb.userId)
            assertEquals(heaterRemote.temperature, heaterDb.temperature)
            assertEquals(heaterRemote.mode, heaterDb.mode.getMode())
            assertEquals(heaterRemote.id, heaterDb.id)
            assertEquals(heaterRemote.name, heaterDb.name)
        }
    }

    @Nested
    inner class RollerShutterRemoteToDbModel {
        @Test
        fun `create RollerShutterDbModel from RollerShutterRemote`() {
            val shutterRemote =
                RollerShutterRemote(
                    id = 1, name = "MyShutter", type = RollerShutterRemote.KEY_PARSER,
                    position = 10
                )
            val userId = 1

            val shutterDb = rollerShutterRemoteToDbModel(shutterRemote, userId)

            assertEquals(userId, shutterDb.userId)
            assertEquals(shutterRemote.position, shutterDb.position)
            assertEquals(shutterRemote.id, shutterDb.id)
            assertEquals(shutterRemote.name, shutterDb.name)
        }
    }

    @Nested
    inner class GetDeviceMode {
        @ParameterizedTest
        @CsvSource("ON", "OFF")
        fun `get device mode according to its string representation`(modeStr: String) {
            val mode = getDeviceMode(modeStr)
            assertEquals(modeStr, mode.getMode())
        }
    }
}
