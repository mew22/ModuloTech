package com.example.modulotech.ui.device.detail.rollershutter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.modulotech.data.DataRepository
import com.example.modulotech.db.device.RollerShutterDbModel
import com.example.modulotech.ui.device.detail.heater.HeaterDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RollerShutterDetailViewModel(private val mRepository: DataRepository) : ViewModel() {
    companion object {
        const val PROFILE_FIELD_MIN_CHAR = 2
    }

    fun getShutterById(id: Int): LiveData<RollerShutterDbModel?> =
        mRepository.getStoredRollerShutterById(id).asLiveData(viewModelScope.coroutineContext)

    fun setPosition(id: Int, value: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getStoredRollerShutterByIdSync(id)?.let {
                mRepository.updateRollerShutter(it.copy(position = value.toInt()))
            }
        }
    }

    fun saveDeviceName(id: Int, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getStoredRollerShutterByIdSync(id)?.let { shutter ->
                mRepository.updateRollerShutter(
                    shutter.copy(
                        name = name
                    )
                )
            }
        }
    }

    fun checkDeviceNameConformity(value: String) =
        value.length >= HeaterDetailViewModel.PROFILE_FIELD_MIN_CHAR
}
