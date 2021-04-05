package com.example.modulotech.data

import com.example.modulotech.db.device.DeviceDao
import com.example.modulotech.db.device.HeaterDbModel
import com.example.modulotech.db.device.LightDbModel
import com.example.modulotech.db.device.RollerShutterDbModel
import com.example.modulotech.db.user.UserDao
import com.example.modulotech.db.user.UserDbModel
import com.example.modulotech.domain.heaterRemoteToDbModel
import com.example.modulotech.domain.lightRemoteToDbModel
import com.example.modulotech.domain.rollerShutterRemoteToDbModel
import com.example.modulotech.domain.userRemoteToDbModel
import com.example.modulotech.service.UserWithDevicesService
import com.example.modulotech.service.model.HeaterRemote
import com.example.modulotech.service.model.LightRemote
import com.example.modulotech.service.model.RollerShutterRemote
import com.example.modulotech.service.model.UserWithDevicesRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataRepository(
    private val mUserWithDevicesService: UserWithDevicesService,
    private val mUserDao: UserDao,
    private val mDeviceDao: DeviceDao
) {

    fun refresh(forced: Boolean = false): Flow<Result<Any>> = flow {
        emit(
            runCatching {
                if (!forced) {
                    if (mUserDao.getFirstUserSync() == null) {
                        insertFromRemote(mUserWithDevicesService.getData())
                    }
                } else {
                    insertFromRemote(mUserWithDevicesService.getData())
                }
            }
        )
    }

    // User
    fun getFirstStoredUser(): Flow<UserDbModel?> =
        mUserDao.getFirstUser()

    fun getFirstStoredUserSync(): UserDbModel? =
        mUserDao.getFirstUserSync()

    fun insertUser(user: UserDbModel) {
        mUserDao.insert(user)
    }

    fun updateUser(user: UserDbModel) {
        mUserDao.update(user)
    }

    fun deleteUser(user: UserDbModel) {
        mUserDao.delete(user)
    }

    // Lights
    fun getStoredLights(): Flow<List<LightDbModel>?> =
        mDeviceDao.getLights()

    fun getStoredLightById(id: Int): Flow<LightDbModel?> =
        mDeviceDao.getLightById(id)

    fun getStoredLightByIdSync(id: Int): LightDbModel? =
        mDeviceDao.getLightByIdSync(id)

    fun insertLights(lights: List<LightDbModel>) {
        mDeviceDao.insertLights(lights)
    }

    fun updateLight(light: LightDbModel) {
        mDeviceDao.updateLight(light)
    }

    fun deleteLight(light: LightDbModel) {
        mDeviceDao.deleteLight(light)
    }

    // Heaters
    fun getStoredHeaters(): Flow<List<HeaterDbModel>?> =
        mDeviceDao.getHeaters()

    fun getStoredHeaterById(id: Int): Flow<HeaterDbModel?> =
        mDeviceDao.getHeaterById(id)

    fun getStoredHeaterByIdSync(id: Int): HeaterDbModel? =
        mDeviceDao.getHeaterByIdSync(id)

    fun insertHeaters(heaters: List<HeaterDbModel>) {
        mDeviceDao.insertHeaters(heaters)
    }

    fun updateHeater(heater: HeaterDbModel) {
        mDeviceDao.updateHeater(heater)
    }

    fun deleteHeater(heater: HeaterDbModel) {
        mDeviceDao.deleteHeater(heater)
    }

    // RollerShutters
    fun getStoredRollerShutters(): Flow<List<RollerShutterDbModel>?> =
        mDeviceDao.getShutters()

    fun getStoredRollerShutterById(id: Int): Flow<RollerShutterDbModel?> =
        mDeviceDao.getShutterById(id)

    fun getStoredRollerShutterByIdSync(id: Int): RollerShutterDbModel? =
        mDeviceDao.getShutterByIdSync(id)

    fun insertRollerShutters(rollerShutters: List<RollerShutterDbModel>) {
        mDeviceDao.insertShutters(rollerShutters)
    }

    fun updateRollerShutter(rollerShutter: RollerShutterDbModel) {
        mDeviceDao.updateShutter(rollerShutter)
    }

    fun deleteRollerShutter(rollerShutter: RollerShutterDbModel) {
        mDeviceDao.deleteShutter(rollerShutter)
    }

    private fun insertFromRemote(data: UserWithDevicesRemote) {
        data.user?.let { user ->
            userRemoteToDbModel(user).let { userStoredNotNull ->
                mUserDao.insert(userStoredNotNull)

                // Transform remote data into db models
                val lightDbList = data.devices?.mapNotNull {
                    if (it is LightRemote)
                        lightRemoteToDbModel(it, userStoredNotNull.userId)
                    else null
                }
                if (!lightDbList.isNullOrEmpty()) {
                    mDeviceDao.insertLights(lightDbList)
                }

                // Transform remote data into db models
                val heaterDbList = data.devices?.mapNotNull {
                    if (it is HeaterRemote)
                        heaterRemoteToDbModel(it, userStoredNotNull.userId)
                    else
                        null
                }
                if (!heaterDbList.isNullOrEmpty()) {
                    mDeviceDao.insertHeaters(heaterDbList)
                }

                // Transform remote data into db models
                val rollerShutterDbList = data.devices?.mapNotNull {
                    if (it is RollerShutterRemote) rollerShutterRemoteToDbModel(
                        it,
                        userStoredNotNull.userId
                    )
                    else null
                }
                if (!rollerShutterDbList.isNullOrEmpty()) {
                    mDeviceDao.insertShutters(rollerShutterDbList)
                }
            }
        }
    }
}
