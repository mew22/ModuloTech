package com.example.modulotech.ui.device.detail.heater

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.modulotech.data.DataRepository
import com.example.modulotech.db.device.DeviceMode
import com.example.modulotech.db.device.HeaterDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeaterDetailViewModel(private val mRepository: DataRepository) : ViewModel() {
    companion object {
        const val PROFILE_FIELD_MIN_CHAR = 2
    }

    fun getHeaterById(id: Int): LiveData<HeaterDbModel?> =
        mRepository.getStoredHeaterById(id).asLiveData(viewModelScope.coroutineContext)

    fun setTemperature(id: Int, value: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getStoredHeaterByIdSync(id)?.let {
                mRepository.updateHeater(it.copy(temperature = value))
            }
        }
    }

    fun turnDevice(id: Int, mode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.getStoredHeaterByIdSync(id)?.let { heater ->
                mRepository.updateHeater(
                    heater.copy(
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
            mRepository.getStoredHeaterByIdSync(id)?.let { heater ->
                mRepository.updateHeater(
                    heater.copy(
                        name = name
                    )
                )
            }
        }
    }

    fun checkDeviceNameConformity(value: String) = value.length >= PROFILE_FIELD_MIN_CHAR
}
