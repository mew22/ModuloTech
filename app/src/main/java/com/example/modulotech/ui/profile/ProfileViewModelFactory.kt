package com.example.modulotech.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modulotech.data.DataRepository

class ProfileViewModelFactory(private val mDataRepository: DataRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(mDataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
