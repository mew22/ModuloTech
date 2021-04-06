package com.example.modulotech.ui.device.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modulotech.data.DataRepository

class DeviceListViewModelFactory(private val mDataRepository: DataRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeviceListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeviceListViewModel(mDataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
