package com.example.modulotech.ui.device.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.modulotech.data.DataRepository
import com.example.modulotech.db.device.DeviceDbModel
import com.example.modulotech.db.device.HeaterDbModel
import com.example.modulotech.db.device.LightDbModel
import com.example.modulotech.db.device.RollerShutterDbModel
import java.util.ArrayList
import kotlin.collections.List
import kotlin.collections.distinct
import kotlin.collections.forEach
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.mutableMapOf
import kotlin.collections.set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class DeviceListViewModel(private val mRepository: DataRepository) : ViewModel() {

    private var mCategoryChecked = mutableMapOf<String, Boolean>()

    val categoryFilter: (DeviceDbModel) -> Boolean = {
        mCategoryChecked[it.getDeviceType()] ?: true
    }

    val deviceList: LiveData<List<DeviceDbModel>?> =
        combine(
            mRepository.getStoredLights(),
            mRepository.getStoredHeaters(),
            mRepository.getStoredRollerShutters()
        ) { lights, heaters, shutters ->
            ArrayList<DeviceDbModel>().apply {
                lights?.let { addAll(it) }
                heaters?.let { addAll(it) }
                shutters?.let { addAll(it) }
            }
        }
            .asLiveData(viewModelScope.coroutineContext)

    val deviceListCategories: LiveData<List<String>?> = Transformations.map(deviceList) {
        it?.map(DeviceDbModel::getDeviceType)?.distinct().also { list ->
            list?.forEach { category -> mCategoryChecked.putIfAbsent(category, true) }
        }
    }

    fun setEnableCategory(category: String, checked: Boolean) {
        mCategoryChecked[category] = checked
    }

    fun refresh(): LiveData<Result<Any>> =
        mRepository.refresh(true).asLiveData(Dispatchers.IO)

    fun deleteDevice(device: DeviceDbModel) {
        viewModelScope.launch(Dispatchers.IO) {
            when (device) {
                is LightDbModel -> mRepository.deleteLight(device)
                is HeaterDbModel -> mRepository.deleteHeater(device)
                is RollerShutterDbModel -> mRepository.deleteRollerShutter(device)
            }
        }
    }

    fun saveDevice(device: DeviceDbModel) {
        viewModelScope.launch(Dispatchers.IO) {
            when (device) {
                is LightDbModel -> mRepository.insertLights(listOf(device))
                is HeaterDbModel -> mRepository.insertHeaters(listOf(device))
                is RollerShutterDbModel -> mRepository.insertRollerShutters(listOf(device))
            }
        }
    }
}
