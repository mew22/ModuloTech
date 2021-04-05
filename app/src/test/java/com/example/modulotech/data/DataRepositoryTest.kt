package com.example.modulotech.data

import app.cash.turbine.test
import com.example.modulotech.db.device.DeviceDao
import com.example.modulotech.db.user.UserDao
import com.example.modulotech.db.user.UserDbModel
import com.example.modulotech.service.UserWithDevicesService
import com.example.modulotech.service.model.HeaterRemote
import com.example.modulotech.service.model.LightRemote
import com.example.modulotech.service.model.RollerShutterRemote
import com.example.modulotech.service.model.UserWithDevicesRemote
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class DataRepositoryTest {

    @MockK
    private lateinit var mUserDao: UserDao

    @MockK
    private lateinit var mDeviceDao: DeviceDao

    @MockK
    private lateinit var mUserWithDevicesService: UserWithDevicesService

    private lateinit var mDataRepository: DataRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        mDataRepository = DataRepository(mUserWithDevicesService, mUserDao, mDeviceDao)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    inner class Refresh {

        @Test
        @ExperimentalTime
        fun `insert data into database in case of forced refresh`() = runBlocking {
            val remoteData =
                UserWithDevicesRemote(
                    listOf(
                        mockk<LightRemote>(relaxed = true),
                        mockk<HeaterRemote>(relaxed = true),
                        mockk<RollerShutterRemote>(relaxed = true)
                    ), mockk(relaxed = true)
                )
            coEvery {
                mUserWithDevicesService.getData()
            } returns remoteData

            every {
                mDeviceDao.insertHeaters(any())
            } returns emptyList()

            every {
                mDeviceDao.insertLights(any())
            } returns emptyList()

            every {
                mDeviceDao.insertShutters(any())
            } returns emptyList()

            every {
                mUserDao.insert(any<UserDbModel>())
            } returns 0

            mDataRepository.refresh(true).test {
                verify(exactly = 1) {
                    mUserDao.insert(any<UserDbModel>())
                }
                verify(exactly = 1) {
                    mDeviceDao.insertShutters(any())
                }
                verify(exactly = 1) {
                    mDeviceDao.insertHeaters(any())
                }
                verify(exactly = 1) {
                    mDeviceDao.insertLights(any())
                }

                assertEquals(Result.success(Unit), expectItem())
                expectComplete()
            }
        }

        @Test
        @ExperimentalTime
        fun `avoid insert data on simple refresh if there is data present in database`() =
            runBlocking {
                coEvery {
                    mUserDao.getFirstUserSync()
                } returns mockk()

                mDataRepository.refresh(false).test {
                    verify(exactly = 0) {
                        mUserDao.insert(any<UserDbModel>())
                    }
                    verify(exactly = 0) {
                        mDeviceDao.insertShutters(any())
                    }
                    verify(exactly = 0) {
                        mDeviceDao.insertHeaters(any())
                    }
                    verify(exactly = 0) {
                        mDeviceDao.insertLights(any())
                    }

                    assertEquals(Result.success(Unit), expectItem())
                    expectComplete()
                }
            }

        @Test
        @ExperimentalTime
        fun `insert data into database if database is empty`() = runBlocking {
            val remoteData =
                UserWithDevicesRemote(
                    listOf(
                        mockk<LightRemote>(relaxed = true),
                        mockk<HeaterRemote>(relaxed = true),
                        mockk<RollerShutterRemote>(relaxed = true)
                    ), mockk(relaxed = true)
                )
            coEvery {
                mUserWithDevicesService.getData()
            } returns remoteData

            coEvery {
                mUserDao.getFirstUserSync()
            } returns null

            every {
                mDeviceDao.insertHeaters(any())
            } returns emptyList()

            every {
                mDeviceDao.insertLights(any())
            } returns emptyList()

            every {
                mDeviceDao.insertShutters(any())
            } returns emptyList()

            every {
                mUserDao.insert(any<UserDbModel>())
            } returns 0

            mDataRepository.refresh(false).test {
                verify(exactly = 1) {
                    mUserDao.insert(any<UserDbModel>())
                }
                verify(exactly = 1) {
                    mDeviceDao.insertShutters(any())
                }
                verify(exactly = 1) {
                    mDeviceDao.insertHeaters(any())
                }
                verify(exactly = 1) {
                    mDeviceDao.insertLights(any())
                }

                assertEquals(Result.success(Unit), expectItem())
                expectComplete()
            }
        }

        /**
         * The 3 above tests merged in a parametrized test.
         * Conserved each to show both way.
         */
        @ExperimentalTime
        @ParameterizedTest
        @CsvSource("true, false", "true, true", "false, true", "false, false")
        fun `insert data into database on forced refresh or database empty`(
            forcedRefresh: Boolean,
            emptyDatabase: Boolean
        ) = runBlocking {
            val remoteData =
                UserWithDevicesRemote(
                    listOf(
                        mockk<LightRemote>(relaxed = true),
                        mockk<HeaterRemote>(relaxed = true),
                        mockk<RollerShutterRemote>(relaxed = true)
                    ), mockk(relaxed = true)
                )
            coEvery {
                mUserWithDevicesService.getData()
            } returns remoteData

            coEvery {
                mUserDao.getFirstUserSync()
            } returns if (emptyDatabase) {
                null
            } else {
                mockk()
            }

            every {
                mDeviceDao.insertHeaters(any())
            } returns emptyList()

            every {
                mDeviceDao.insertLights(any())
            } returns emptyList()

            every {
                mDeviceDao.insertShutters(any())
            } returns emptyList()

            every {
                mUserDao.insert(any<UserDbModel>())
            } returns 0

            mDataRepository.refresh(forcedRefresh).test {
                verify(
                    exactly = if (forcedRefresh || emptyDatabase) {
                        1
                    } else {
                        0
                    }
                ) {
                    mUserDao.insert(any<UserDbModel>())
                }

                verify(
                    exactly = if (forcedRefresh || emptyDatabase) {
                        1
                    } else {
                        0
                    }
                ) {
                    mDeviceDao.insertShutters(any())
                }

                verify(
                    exactly = if (forcedRefresh || emptyDatabase) {
                        1
                    } else {
                        0
                    }
                ) {
                    mDeviceDao.insertHeaters(any())
                }

                verify(
                    exactly = if (forcedRefresh || emptyDatabase) {
                        1
                    } else {
                        0
                    }
                ) {
                    mDeviceDao.insertLights(any())
                }

                assertEquals(Result.success(Unit), expectItem())
                expectComplete()
            }
        }
    }

    @Nested
    inner class GetFirstStoredUser {

        @Test
        fun `call user dao #getFirstUser`() {
            every {
                mUserDao.getFirstUser()
            } returns mockk()

            mDataRepository.getFirstStoredUser()

            verify(exactly = 1) {
                mUserDao.getFirstUser()
            }
        }
    }

    @Nested
    inner class GetFirstStoredUserSync {

        @Test
        fun `call user dao #getFirstUserSync`() {
            every {
                mUserDao.getFirstUserSync()
            } returns mockk()

            mDataRepository.getFirstStoredUserSync()

            verify(exactly = 1) {
                mUserDao.getFirstUserSync()
            }
        }
    }

    @Nested
    inner class InsertUser {

        @Test
        fun `call user dao #insert`() {
            every {
                mUserDao.insert(any<UserDbModel>())
            } returns 0

            mDataRepository.insertUser(mockk())

            verify(exactly = 1) {
                mUserDao.insert(any<UserDbModel>())
            }
        }
    }

    @Nested
    inner class DeleteUser {

        @Test
        fun `call user dao #delete`() {
            justRun { mUserDao.delete(any<UserDbModel>()) }

            mDataRepository.deleteUser(mockk())

            verify(exactly = 1) {
                mUserDao.delete(any<UserDbModel>())
            }
        }
    }

    @Nested
    inner class GetStoredHeaters {

        @Test
        fun `call device dao #getHeaters`() {
            every {
                mDeviceDao.getHeaters()
            } returns mockk()

            mDataRepository.getStoredHeaters()

            verify(exactly = 1) {
                mDeviceDao.getHeaters()
            }
        }
    }

    @Nested
    inner class GetStoredHeaterById {

        @Test
        fun `call device dao #getHeaterById`() {
            every {
                mDeviceDao.getHeaterById(any())
            } returns mockk()

            mDataRepository.getStoredHeaterById(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.getHeaterById(any())
            }
        }
    }

    @Nested
    inner class GetStoredHeaterByIdSync {

        @Test
        fun `call user dao #getHeaterByIdSync`() {
            every {
                mDeviceDao.getHeaterByIdSync(any())
            } returns mockk()

            mDataRepository.getStoredHeaterByIdSync(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.getHeaterByIdSync(any())
            }
        }
    }

    @Nested
    inner class InsertHeaters {

        @Test
        fun `call user dao #insertHeaters`() {
            every {
                mDeviceDao.insertHeaters(any())
            } returns mockk()

            mDataRepository.insertHeaters(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.insertHeaters(any())
            }
        }
    }

    @Nested
    inner class UpdateHeater {

        @Test
        fun `call user dao #updateHeater`() {
            every {
                mDeviceDao.updateHeater(any())
            } returns mockk()

            mDataRepository.updateHeater(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.updateHeater(any())
            }
        }
    }

    @Nested
    inner class DeleteHeater {

        @Test
        fun `call user dao #deleteHeater`() {
            every {
                mDeviceDao.deleteHeater(any())
            } returns mockk()

            mDataRepository.deleteHeater(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.deleteHeater(any())
            }
        }
    }

    @Nested
    inner class GetStoredRollerShutters {

        @Test
        fun `call device dao #getShutters`() {
            every {
                mDeviceDao.getShutters()
            } returns mockk()

            mDataRepository.getStoredRollerShutters()

            verify(exactly = 1) {
                mDeviceDao.getShutters()
            }
        }
    }

    @Nested
    inner class GetStoredRollerShutterById {

        @Test
        fun `call device dao #getShutterById`() {
            every {
                mDeviceDao.getShutterById(any())
            } returns mockk()

            mDataRepository.getStoredRollerShutterById(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.getShutterById(any())
            }
        }
    }

    @Nested
    inner class GetStoredRollerShutterByIdSync {

        @Test
        fun `call user dao #getShutterByIdSync`() {
            every {
                mDeviceDao.getShutterByIdSync(any())
            } returns mockk()

            mDataRepository.getStoredRollerShutterByIdSync(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.getShutterByIdSync(any())
            }
        }
    }

    @Nested
    inner class InsertRollerShutters {

        @Test
        fun `call user dao #insertShutters`() {
            every {
                mDeviceDao.insertShutters(any())
            } returns mockk()

            mDataRepository.insertRollerShutters(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.insertShutters(any())
            }
        }
    }

    @Nested
    inner class UpdateRollerShutter {

        @Test
        fun `call user dao #updateShutter`() {
            every {
                mDeviceDao.updateShutter(any())
            } returns mockk()

            mDataRepository.updateRollerShutter(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.updateShutter(any())
            }
        }
    }

    @Nested
    inner class DeleteRollerShutter {

        @Test
        fun `call user dao #deleteShutter`() {
            every {
                mDeviceDao.deleteShutter(any())
            } returns mockk()

            mDataRepository.deleteRollerShutter(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.deleteShutter(any())
            }
        }
    }

    @Nested
    inner class GetStoredLights {

        @Test
        fun `call device dao #getLights`() {
            every {
                mDeviceDao.getLights()
            } returns mockk()

            mDataRepository.getStoredLights()

            verify(exactly = 1) {
                mDeviceDao.getLights()
            }
        }
    }

    @Nested
    inner class GetStoredLightById {

        @Test
        fun `call device dao #getLightById`() {
            every {
                mDeviceDao.getLightById(any())
            } returns mockk()

            mDataRepository.getStoredLightById(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.getLightById(any())
            }
        }
    }

    @Nested
    inner class GetStoredLightByIdSync {

        @Test
        fun `call user dao #getLightByIdSync`() {
            every {
                mDeviceDao.getLightByIdSync(any())
            } returns mockk()

            mDataRepository.getStoredLightByIdSync(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.getLightByIdSync(any())
            }
        }
    }

    @Nested
    inner class InsertLights {

        @Test
        fun `call user dao #insertLights`() {
            every {
                mDeviceDao.insertLights(any())
            } returns mockk()

            mDataRepository.insertLights(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.insertLights(any())
            }
        }
    }

    @Nested
    inner class UpdateLight {

        @Test
        fun `call user dao #updateLight`() {
            every {
                mDeviceDao.updateLight(any())
            } returns mockk()

            mDataRepository.updateLight(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.updateLight(any())
            }
        }
    }

    @Nested
    inner class DeleteLight {

        @Test
        fun `call user dao #deleteLight`() {
            every {
                mDeviceDao.deleteLight(any())
            } returns mockk()

            mDataRepository.deleteLight(mockk(relaxed = true))

            verify(exactly = 1) {
                mDeviceDao.deleteLight(any())
            }
        }
    }
}
