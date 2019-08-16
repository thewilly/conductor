package io.github.thewilly.conductor.server.services

import io.github.thewilly.conductor.server.repositories.DevicesRepository
import io.github.thewilly.conductor.server.types.Channel
import io.github.thewilly.conductor.server.types.Device
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
        val newDevice = Device(deviceName = name, location = location, deviceMac = mac, isOff = false)
        val stored: Device? = devicesRepo!!.findByDeviceMac(newDevice.deviceMac)
        if(stored == null) {
            devicesRepo!!.save(newDevice)
            return newDevice;
        }
        return stored
    }

    fun unregister(deviceToken: String): Device? {
        val authDevice = devicesRepo!!.findByDeviceToken(deviceToken)
        if(authDevice != null) {
            devicesRepo!!.delete(authDevice)
            return authDevice;
        }

        return authDevice;
    }

    fun authenticate(deviceToken:String): Device {
        val authDevice = devicesRepo!!.findByDeviceToken(deviceToken)
        authDevice.lastAuthDate = Date.from(Instant.now());
        devicesRepo.save(authDevice)
        return authDevice
    }

    fun changeListeningChannel( deviceToken: String, newChannel: Channel ) {
        val authDevice = devicesRepo!!.findByDeviceToken(deviceToken)
        authDevice.listenningChannel = newChannel;
        devicesRepo.save(authDevice)
    }

    fun turnOff(deviceToken: String): Boolean {
        val authDevice = devicesRepo!!.findByDeviceToken(deviceToken)
        if(authDevice.isOff)
            return false
        authDevice.isOff = true
        devicesRepo.save(authDevice)
        return true;
    }

    fun turnOn(deviceToken: String): Boolean {
        val authDevice = devicesRepo!!.findByDeviceToken(deviceToken)
        if(authDevice.isOff) {
            authDevice.isOff = false
            devicesRepo.save(authDevice)
            return true;
        }
        return false;
    }

    fun getDeviceStatus(deviceToken: String): String {
        val authDevice = devicesRepo!!.findByDeviceToken(deviceToken)
        if(authDevice.isOff)
            return "off"
        return "on"
    }
}
