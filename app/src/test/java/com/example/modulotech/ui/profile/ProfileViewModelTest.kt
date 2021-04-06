package com.example.modulotech.ui.profile

import androidx.lifecycle.asFlow
import com.example.modulotech.data.DataRepository
import com.example.modulotech.db.user.AddressDbModel
import com.example.modulotech.db.user.UserDbModel
import com.example.modulotech.util.CoroutinesTestExtension
import com.example.modulotech.util.InstantExecutorExtension
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

internal class ProfileViewModelTest {

    companion object {
        const val DATE_PATTERN = "dd-MM-yyyy"
    }

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @MockK
    private lateinit var mRepository: DataRepository

    private lateinit var mViewModel: ProfileViewModel

    private val mUser = UserDbModel(
        firstName = "John", lastName = "Doe", birthDate = Date.from(Instant.now()),
        address = AddressDbModel(
            city = "Paris", postalCode = 75000,
            street = "Rue Montbrun", streetCode = "36", country = "France"
        )
    )

    private val mFirstUser: Flow<UserDbModel?> = flow { emit(mUser) }

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        every {
            mRepository.getFirstStoredUser()
        } returns mFirstUser
        mViewModel = ProfileViewModel(mRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    inner class FirstUser {
        @Test
        @ExtendWith(InstantExecutorExtension::class)
        fun `returns the same Flow as DataRepository#getFirstUser`() {
            val flow: Flow<UserDbModel?> = flow { emit(mUser) }
            every {
                mRepository.getFirstStoredUser()
            } returns flow

            runBlockingTest {
                assertEquals(
                    flow.first(),
                    mViewModel.firstUser.asFlow().first()
                )
            }
        }
    }

    @Nested
    inner class SaveUser {
        @Test
        @ExtendWith(InstantExecutorExtension::class)
        fun `should calls mRepository#updateUser`() = runBlockingTest {
            every {
                mRepository.getFirstStoredUserSync()
            } returns mUser

            every {
                mRepository.updateUser(any())
            } just Runs

            val birthday = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).parse(
                SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(
                    Date.from(
                        Instant.now()
                    )
                )
            )
            val user = UserDbModel(
                firstName = "Test", lastName = "Test", birthDate = birthday!!,
                address = AddressDbModel(
                    city = "Test", postalCode = 56000,
                    street = "Test", streetCode = "42", country = "Germany"
                )
            )
            mViewModel.saveUser(
                firstName = user.firstName,
                lastName = user.lastName,
                birthdate = SimpleDateFormat(
                    DATE_PATTERN,
                    Locale.getDefault()
                ).format(user.birthDate),
                city = user.address.city,
                postalCode = user.address.postalCode.toString(),
                street = user.address.street,
                streetCode = user.address.streetCode,
                country = user.address.country
            )

            verify(exactly = 1) {
                mRepository.updateUser(user)
            }
        }
    }

    @Nested
    inner class FormatDate {
        @Test
        fun `returns date format dd-MM-yyyy`() {
            val date = Date.from(Instant.now())
            val format = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())

            assertEquals(format.format(date), mViewModel.formatDate(date))
        }
    }

    @Nested
    inner class CheckFirstNameConformity {
        @Test
        fun `returns compliant from a good input`() {
            val goodValue = "MyName"
            assertTrue(mViewModel.checkFirstNameConformity(goodValue))
        }

        @Test
        fun `returns not compliant from a bad input`() {
            val badValue = "M"
            assertFalse(mViewModel.checkFirstNameConformity(badValue))
        }
    }

    @Nested
    inner class CheckLastNameConformity {
        @Test
        fun `returns compliant from a good input`() {
            val goodValue = "MyName"
            assertTrue(mViewModel.checkLastNameConformity(goodValue))
        }

        @Test
        fun `returns not compliant from a bad input`() {
            val badValue = "M"
            assertFalse(mViewModel.checkLastNameConformity(badValue))
        }
    }

    @Nested
    inner class CheckCityConformity {
        @Test
        fun `returns compliant from a good input`() {
            val goodValue = "MyCity"
            assertTrue(mViewModel.checkCityConformity(goodValue))
        }

        @Test
        fun `returns not compliant from a bad input`() {
            val badValue = "M"
            assertFalse(mViewModel.checkCityConformity(badValue))
        }
    }

    @Nested
    inner class CheckPostalCodeConformity {
        @Test
        fun `returns compliant from a good input`() {
            val goodValue = "75000"
            assertTrue(mViewModel.checkPostalCodeConformity(goodValue))
        }

        @Test
        fun `returns not compliant from a bad input`() {
            val badValue = "M"
            assertFalse(mViewModel.checkPostalCodeConformity(badValue))
        }
    }

    @Nested
    inner class CheckStreetConformity {
        @Test
        fun `returns compliant from a good input`() {
            val goodValue = "MyStreet"
            assertTrue(mViewModel.checkStreetConformity(goodValue))
        }

        @Test
        fun `returns not compliant from a bad input`() {
            val badValue = "M"
            assertFalse(mViewModel.checkStreetConformity(badValue))
        }
    }

    @Nested
    inner class CheckStreetCodeConformity {
        @Test
        fun `returns compliant from a good input`() {
            val goodValue = "15"
            assertTrue(mViewModel.checkStreetCodeConformity(goodValue))
        }

        @Test
        fun `returns not compliant from a bad input`() {
            val badValue = "M"
            assertFalse(mViewModel.checkStreetCodeConformity(badValue))
        }
    }

    @Nested
    inner class CheckCountryConformity {
        @Test
        fun `returns compliant from a good input`() {
            val goodValue = "MyCountry"
            assertTrue(mViewModel.checkCountryConformity(goodValue))
        }

        @Test
        fun `returns not compliant from a bad input`() {
            val badValue = "M"
            assertFalse(mViewModel.checkCountryConformity(badValue))
        }
    }
}
