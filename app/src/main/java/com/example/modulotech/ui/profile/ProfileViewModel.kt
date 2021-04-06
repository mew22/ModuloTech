package com.example.modulotech.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.modulotech.data.DataRepository
import com.example.modulotech.db.user.AddressDbModel
import com.example.modulotech.db.user.UserDbModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val mRepository: DataRepository) : ViewModel() {
    companion object {
        const val TAG = "ProfileViewModel"
        const val DATE_FORMAT = "dd-MM-yyyy"
        const val PROFILE_FIELD_MIN_CHAR = 2
        const val POSTAL_CODE_FIELD_CHAR = 5
        const val STREET_CODE_FIELD_CHAR = 2
    }

    val firstUser: LiveData<UserDbModel?> =
        mRepository.getFirstStoredUser().asLiveData(viewModelScope.coroutineContext)

    fun saveUser(
        firstName: String,
        lastName: String,
        birthdate: String,
        city: String,
        postalCode: String,
        street: String,
        streetCode: String,
        country: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getFirstStoredUserSync()?.let {
                val firstNameToSave = if (checkFirstNameConformity(firstName)) {
                    firstName
                } else it.firstName
                val lastNameToSave = if (checkLastNameConformity(lastName)) {
                    lastName
                } else it.lastName
                val birthdayToSave =
                    SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(birthdate)
                        ?: it.birthDate
                val cityToSave = if (checkCityConformity(city)) {
                    city
                } else it.address.city
                val postalCodeToSave = if (checkPostalCodeConformity(postalCode)) {
                    postalCode.toInt()
                } else it.address.postalCode
                val streetToSave = if (checkStreetConformity(street)) {
                    street
                } else it.address.street
                val streetCodeToSave = if (checkStreetCodeConformity(streetCode)) {
                    streetCode
                } else it.address.streetCode
                val countryToSave = if (checkCountryConformity(country)) {
                    country
                } else it.address.country

                val user = it.copy(
                    firstName = firstNameToSave,
                    lastName = lastNameToSave,
                    birthDate = birthdayToSave,
                    address = AddressDbModel(
                        city = cityToSave,
                        postalCode = postalCodeToSave,
                        street = streetToSave,
                        streetCode = streetCodeToSave,
                        country = countryToSave
                    )
                )
                mRepository.updateUser(
                    user
                )
            }
        }
    }

    fun formatDate(date: Date): String =
        SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date)

    fun checkFirstNameConformity(value: String): Boolean = value.length >= PROFILE_FIELD_MIN_CHAR
    fun checkLastNameConformity(value: String): Boolean = value.length >= PROFILE_FIELD_MIN_CHAR
    fun checkCityConformity(value: String): Boolean = value.length >= PROFILE_FIELD_MIN_CHAR
    fun checkPostalCodeConformity(value: String): Boolean {
        try {
            value.toInt()
        } catch (ex: java.lang.NumberFormatException) {
            return false
        }
        return value.length == POSTAL_CODE_FIELD_CHAR
    }

    fun checkStreetConformity(value: String): Boolean = value.length >= PROFILE_FIELD_MIN_CHAR
    fun checkStreetCodeConformity(value: String): Boolean = value.length == STREET_CODE_FIELD_CHAR
    fun checkCountryConformity(value: String): Boolean = value.length >= PROFILE_FIELD_MIN_CHAR
}
