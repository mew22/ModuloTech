package com.example.modulotech.service

import com.example.modulotech.service.model.HeaterRemote
import com.example.modulotech.service.model.LightRemote
import com.example.modulotech.service.model.RollerShutterRemote
import com.example.modulotech.service.model.UserRemote
import com.example.modulotech.service.model.UserWithDevicesRemote
import com.example.modulotech.util.MockResponseFileReader
import com.example.modulotech.util.enqueueResponse
import com.google.gson.reflect.TypeToken
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class UserWithDevicesServiceTest {

    private lateinit var mMockWebServer: MockWebServer

    private lateinit var mRemoteService: UserWithDevicesService

    @BeforeEach
    fun setUp() {
        mMockWebServer = MockWebServer()
        mMockWebServer.start()

        mockkObject(UserWithDevicesService.Companion)
        every {
            UserWithDevicesService.create()
        } returns Retrofit.Builder()
            .baseUrl(mMockWebServer.url("/"))
            .client(HttpClient.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonParser.gson))
            .build()
            .create(UserWithDevicesService::class.java)

        mRemoteService = UserWithDevicesService.create()
    }

    @AfterEach
    fun tearDown() {
        mMockWebServer.shutdown()
    }

    @Nested
    inner class GetData {
        @Test
        fun `should fetch data correctly given 200 response`() {
            mMockWebServer.enqueueResponse("data_service_truncated.json", 200)

            runBlocking {
                val expected: UserWithDevicesRemote =
                    GsonParser.gson.fromJson(
                        MockResponseFileReader("data_service_truncated.json").content,
                        object : TypeToken<UserWithDevicesRemote>() {}.type
                    )

                assertEquals(expected, mRemoteService.getData())
            }
        }
    }

    @Nested
    inner class GsonParsing

    @Test
    fun `parse json sample to instantiate UserWithDevicesRemote`() {
        val data: UserWithDevicesRemote =
            GsonParser.gson.fromJson(
                MockResponseFileReader("data_service_truncated.json").content,
                object : TypeToken<UserWithDevicesRemote>() {}.type
            )

        assertTrue(data.user is UserRemote)
        assertTrue(data.devices!![0] is LightRemote)
        assertTrue(data.devices!![1] is RollerShutterRemote)
        assertTrue(data.devices!![2] is HeaterRemote)
    }
}
