package io.github.thewilly.conductor.server.services

import io.github.thewilly.conductor.server.getPrivateKeyFromString
import io.github.thewilly.conductor.server.repositories.DevicesRepository
import io.github.thewilly.conductor.server.types.Channel
import io.github.thewilly.conductor.server.types.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.stereotype.Service
import io.github.thewilly.conductor.server.getPublicKeyFromString
import org.apache.commons.lang3.RandomStringUtils
import java.util.*
import javax.crypto.Cipher
import kong.unirest.Unirest
import org.json.JSONObject


@EntityScan
@Service
class DevicesService {

    @Autowired
    val devicesRepo: DevicesRepository? = null

    fun create(mac: String, ip: String = "0.0.0.0", location: String, key: String): Boolean {
        val newDevice = Device(mac = mac, ip = ip, location = location, key = key)
        val stored = devicesRepo!!.findByMac(newDevice.mac)
        if(stored == null) {
            devicesRepo.save(newDevice)
            return true;
        }
        return false;
    }

    fun register(mac:String, ip: String): Boolean {
        val stored = devicesRepo!!.findByMac(mac)
        if(stored != null && stored.ip.equals(ip)) {

            // Create a secret
            val secret = RandomStringUtils.randomAlphanumeric(1024)

            // Encrypt the created secret with the device public key.
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKeyFromString(stored.key))
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

    fun unregister(mac: String): Device? {
        val authDevice = devicesRepo!!.findByMac(mac)
        if(authDevice != null) {
            devicesRepo.delete(authDevice)
            return authDevice;
        }

        return authDevice;
    }

    fun setListeningChannel( mac: String, newChannel: Channel ) {
        val authDevice = devicesRepo!!.findByMac(mac)
        authDevice!!.channel = newChannel;
        devicesRepo.save(authDevice)
    }

    fun turnOn(mac: String): Boolean {
        val authDevice = devicesRepo!!.findByMac(mac)
        if(authDevice!!.isOn)
            return false
        authDevice.isOn = true
        devicesRepo.save(authDevice)
        return true;
    }

    fun turnOff(mac: String): Boolean {
        val authDevice = devicesRepo!!.findByMac(mac)
        if(authDevice!!.isOn) {
            authDevice.isOn = false
            devicesRepo.save(authDevice)
            return true;
        }
        return false;
    }

    fun getDeviceStatus(mac: String): String {
        val authDevice = devicesRepo!!.findByMac(mac)
        if(authDevice!!.isOn)
            return "on"
        return "off"
    }

    fun setDeviceIP(mac: String, deviceIP: String) {
        val authDevice = devicesRepo!!.findByMac(mac)
        authDevice!!.ip = deviceIP
        devicesRepo.save(authDevice)
    }
}
