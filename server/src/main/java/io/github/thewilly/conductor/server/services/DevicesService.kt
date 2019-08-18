package io.github.thewilly.conductor.server.services

import io.github.thewilly.conductor.server.getPrivateKeyFromString
import io.github.thewilly.conductor.server.repositories.DevicesRepository
import io.github.thewilly.conductor.server.types.Channel
import io.github.thewilly.conductor.server.types.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.stereotype.Service
import io.github.thewilly.conductor.server.getPublicKeyFromString
import io.github.thewilly.conductor.server.types.DeviceStatus
import org.apache.commons.lang3.RandomStringUtils
import java.util.*
import javax.crypto.Cipher
import kong.unirest.Unirest
import org.json.JSONObject
import java.awt.geom.Point2D


@EntityScan
@Service
class DevicesService {

    @Autowired
    val devicesRepo: DevicesRepository? = null

    fun create(imsi: String, ip: String = "0.0.0.0", location: Point2D.Float, key: String): Boolean {
        val newDevice = Device(imsi = imsi, ip = ip, location = location, publicKey = key)
        val stored = devicesRepo!!.findByImsi(newDevice.imsi)
        if(stored == null) {
            devicesRepo.save(newDevice)
            return true;
        }
        return false;
    }

    fun getAllDevices(): List<Device> {
        return devicesRepo!!.findAll()
    }

    fun register(imsi:String, ip: String): Boolean {
        val stored = devicesRepo!!.findByImsi(imsi)
        if(stored != null && stored.ip.equals(ip)) {

            // Create a secret
            val secret = RandomStringUtils.randomAlphanumeric(512)

            // Encrypt the created secret with the device public key.
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKeyFromString(stored.publicKey))
            val cipherText = cipher.doFinal(secret.toByteArray(Charsets.UTF_8))
            val encryptedSecret = Base64.getEncoder().encodeToString(cipherText)

            // Send the encrypted secret to the device
            val payload = JSONObject()
            payload.put("secret",encryptedSecret)
            val response = Unirest.post(stored.ip + "/api/register").body(payload).asJson()
            val returnedSecret = response.body.`object`.get("secret")

            // Decrypt the returned secret
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKeyFromString(""))
            val returnedCipheredSecret = cipher.doFinal(secret.toByteArray(Charsets.UTF_8))
            val returnedUnEncryptedSecret = Base64.getEncoder().encodeToString(returnedCipheredSecret)

            // Compare initial an returned secrets.
            if(secret.equals(returnedUnEncryptedSecret)) {
                return true;
            } else {
                println("Secret: $secret")
                println("Received one: $returnedUnEncryptedSecret")
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