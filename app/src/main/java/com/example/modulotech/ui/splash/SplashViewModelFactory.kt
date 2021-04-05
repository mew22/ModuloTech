package com.example.modulotech.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modulotech.data.DataRepository

class SplashViewModelFactory(private val mDataRepository: DataRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(mDataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
