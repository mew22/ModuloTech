package com.example.modulotech.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.modulotech.data.DataRepository
import kotlinx.coroutines.Dispatchers

class SplashViewModel(private val mRepository: DataRepository) : ViewModel() {

    fun loadResources(): LiveData<Result<Any>> = mRepository.refresh().asLiveData(Dispatchers.IO)
}
