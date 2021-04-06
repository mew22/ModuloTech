package com.example.modulotech.ui.device.detail.rollershutter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modulotech.data.DataRepository

class RollerShutterDetailViewModelFactory(private val mDataRepository: DataRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RollerShutterDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RollerShutterDetailViewModel(mDataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
