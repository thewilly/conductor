package io.github.thewilly.conductor.server.services

import io.github.thewilly.conductor.server.repositories.DevicesRepository
import io.github.thewilly.conductor.server.types.Device
import io.github.thewilly.conductor.server.types.Token
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@EntityScan
@Service
class DevicesService {

    @Autowired
    val devicesRepo: DevicesRepository? = null

    fun register(name: String, location: String, mac: String): Device {
        val newDevice = Device(deviceName = name, location = location, deviceMac = mac)
        val stored: Device? = devicesRepo!!.findByDeviceMac(newDevice.deviceMac)
        if(stored == null) {
            devicesRepo!!.save(newDevice)
            return newDevice;
        }
        return stored
    }

    fun unregister(deviceToken: String): Device? {
        val storedDevice = devicesRepo!!.findByDeviceToken(deviceToken);
        if(storedDevice != null) {
            devicesRepo!!.delete(storedDevice)
            return storedDevice;
        }

        return storedDevice;
    }

    fun authenticate(deviceToken:String): Device {
        val authDevice = devicesRepo!!.findByDeviceToken(deviceToken)
        authDevice.lastAuthDate = Date.from(Instant.now());
        devicesRepo.save(authDevice)
        return authDevice
    }
}
