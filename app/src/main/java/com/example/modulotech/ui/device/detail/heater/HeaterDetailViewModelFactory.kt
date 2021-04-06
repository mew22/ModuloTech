package com.example.modulotech.ui.device.detail.heater

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modulotech.data.DataRepository

class HeaterDetailViewModelFactory(private val mDataRepository: DataRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeaterDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HeaterDetailViewModel(mDataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
