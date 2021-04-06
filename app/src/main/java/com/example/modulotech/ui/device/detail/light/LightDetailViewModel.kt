package com.example.modulotech.ui.device.detail.light

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.modulotech.data.DataRepository
import com.example.modulotech.db.device.DeviceMode
import com.example.modulotech.db.device.LightDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LightDetailViewModel(private val mRepository: DataRepository) : ViewModel() {
    companion object {
        const val PROFILE_FIELD_MIN_CHAR = 2
    }

    fun getLightById(id: Int): LiveData<LightDbModel?> =
        mRepository.getStoredLightById(id).asLiveData(viewModelScope.coroutineContext)

    fun setIntensity(id: Int, value: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getStoredLightByIdSync(id)?.let {
                mRepository.updateLight(it.copy(intensity = value.toInt()))
            }
        }
    }

    fun turnDevice(id: Int, mode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getStoredLightByIdSync(id)?.let { light ->
                mRepository.updateLight(
                    light.copy(
                        mode = if (mode) {
                            DeviceMode.DeviceOn
                        } else {
                            DeviceMode.DeviceOff
                        }
                    )
                )
            }
        }
    }

    fun saveDeviceName(id: Int, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getStoredLightByIdSync(id)?.let { light ->
                mRepository.updateLight(
                    light.copy(
                        name = name
                    )
                )
            }
        }
    }

    fun checkDeviceNameConformity(value: String) = value.length >= PROFILE_FIELD_MIN_CHAR
}
