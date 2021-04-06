package com.example.modulotech.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.modulotech.data.DataRepository
import com.example.modulotech.db.DeviceDatabase
import com.example.modulotech.service.UserWithDevicesService
import com.example.modulotech.ui.device.detail.heater.HeaterDetailViewModelFactory
import com.example.modulotech.ui.device.detail.light.LightDetailViewModelFactory
import com.example.modulotech.ui.device.detail.rollershutter.RollerShutterDetailViewModelFactory
import com.example.modulotech.ui.device.list.DeviceListViewModelFactory
import com.example.modulotech.ui.profile.ProfileViewModelFactory
import com.example.modulotech.ui.splash.SplashViewModelFactory

object Injection {

    private fun provideDataRepository(context: Context): DataRepository {
        val database = DeviceDatabase.getInstance(context)
        return DataRepository(
            UserWithDevicesService.create(), database.userDao(), database.deviceDao()
        )
    }

    fun provideSplashViewModelFactory(context: Context): ViewModelProvider.Factory {
        return SplashViewModelFactory(provideDataRepository(context))
    }

    fun provideProfileViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ProfileViewModelFactory(provideDataRepository(context))
    }

    fun provideDeviceListViewModelFactory(context: Context): ViewModelProvider.Factory {
        return DeviceListViewModelFactory(provideDataRepository(context))
    }
}
