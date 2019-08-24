package io.github.thewilly.conductor.server.services

import io.github.thewilly.conductor.server.getPrivateKeyFromString
import io.github.thewilly.conductor.server.repositories.DevicesRepository
import io.github.thewilly.conductor.server.types.Channel
import io.github.thewilly.conductor.server.types.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.stereotype.Service
import io.github.thewilly.conductor.server.types.DeviceStatus
import kong.unirest.Unirest
import java.awt.geom.Point2D
import java.security.MessageDigest
import java.nio.charset.Charset
import java.util.*


@EntityScan
@Service
class DevicesService {

    @Autowired
    val devicesRepo: DevicesRepository? = null

    fun remove(deviceId: String) {
        devicesRepo!!.delete(devicesRepo!!.findByDeviceId(deviceId))
    }

    fun create(imsi: String, ip: String = "0.0.0.0", location: Point2D.Float, secret: String): Boolean {
        val newDevice = Device(imsi = imsi, ip = ip, location = location, secret = secret)
        val stored = devicesRepo!!.findByImsi(newDevice.imsi)
        if(stored == null) {
            devicesRepo.save(newDevice)
            return true;
        }
        return false;
    }

    fun create(imsi: String, secret: String) {
        val newDevice = Device(imsi = imsi, secret = secret)
        val stored = devicesRepo!!.findByImsi(newDevice.imsi)
        if(stored == null) {
            devicesRepo.save(newDevice)
        }
    }

    fun getAllDevices(): List<Device> {
        return devicesRepo!!.findAll()
    }

    fun numberOfDevices(): Int {
        return this.getAllDevices().size
    }

    fun numberOfRegisteredDevices(): Int {
        return devicesRepo!!.findByIsRegistered(true)!!.size
    }

    fun numberOfActiveDevices(): Int {
        return devicesRepo!!.findByStatus(DeviceStatus.STAND_BY)!!.size
        + devicesRepo!!.findByStatus(DeviceStatus.RX)!!.size
        + devicesRepo!!.findByStatus(DeviceStatus.TX)!!.size
        + devicesRepo!!.findByStatus(DeviceStatus.UPDATING)!!.size
    }

    fun register(imsi:String, ip: String): Boolean {
        val stored = devicesRepo!!.findByImsi(imsi)
        if(stored != null && stored.ip.equals(ip)) {
            val digest = MessageDigest.getInstance("SHA-256")
            val challenge = digest.digest(stored.secret.toByteArray(Charset.defaultCharset()))

            val challengeString = Base64.getEncoder().encodeToString(challenge)

            // Send the encrypted secret to the device
            val response = Unirest.get( "http://"+stored.ip+"/api/register").asJson()
            val challengeResponse = response.body.`object`.get("challenge-response")

            // Compare initial an returned secrets.
            if(challengeString.equals(challengeResponse)) {

                stored.isRegistered = true
                devicesRepo.save(stored)

                return true;
            } else {
                println("Secret: $challengeString")
                println("Received one: $challengeResponse")
            }
        }
        return false;
    }

    fun unregister(imsi: String): Device? {
        val authDevice = devicesRepo!!.findByImsi(imsi)
        if(authDevice != null) {
            devicesRepo.delete(authDevice)
            return authDevice;
        }

        return authDevice;
    }

    fun setListeningChannel( imsi: String, newChannel: Channel ) {
        val authDevice = devicesRepo!!.findByImsi(imsi)
        authDevice!!.channel = newChannel;
        devicesRepo.save(authDevice)
    }

    fun turnOn(imsi: String): Boolean {
        val authDevice = devicesRepo!!.findByImsi(imsi)
        if(authDevice!!.status == DeviceStatus.OFF) {
            authDevice.status = DeviceStatus.STAND_BY
            devicesRepo.save(authDevice)
            return true
        }
        return false
    }

    fun turnOff(imsi: String): Boolean {
        val authDevice = devicesRepo!!.findByImsi(imsi)
        if(authDevice!!.status != DeviceStatus.OFF) {
            authDevice.status = DeviceStatus.OFF
            devicesRepo.save(authDevice)
            return true;
        }
        return false;
    }

    fun setDeviceIP(imsi: String, deviceIP: String) {
        val authDevice = devicesRepo!!.findByImsi(imsi)
        authDevice!!.ip = deviceIP
        devicesRepo.save(authDevice)
    }
}
