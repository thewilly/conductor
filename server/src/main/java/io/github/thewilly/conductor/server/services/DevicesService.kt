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

    fun remove(deviceId: String) {
        devicesRepo!!.delete(devicesRepo!!.findByDeviceId(deviceId))
    }

    fun create(imsi: String, ip: String = "0.0.0.0", location: Point2D.Float, key: String): Boolean {
        val newDevice = Device(imsi = imsi, ip = ip, location = location, publicKey = key)
        val stored = devicesRepo!!.findByImsi(newDevice.imsi)
        if(stored == null) {
            devicesRepo.save(newDevice)
            return true;
        }
        return false;
    }

    fun create(imsi: String, publicKey: String) {
        val newDevice = Device(imsi = imsi, publicKey = publicKey)
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

            // Create a secret
            val secret = RandomStringUtils.randomAlphanumeric(16)

            // Encrypt the created secret with the device public key.
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKeyFromString(stored.publicKey))
            val cipherText = cipher.doFinal(secret.toByteArray(Charsets.UTF_8))
            val encryptedSecret = Base64.getEncoder().encodeToString(cipherText)
            println(encryptedSecret)

            // Send the encrypted secret to the device
            val payload = JSONObject()
            payload.put("secret",encryptedSecret)
            val response = Unirest.post( "http://localhost:8081/api/register").body(payload).asJson()
            val returnedSecret = response.body.`object`.get("secret")

            // Decrypt the returned secret
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKeyFromString("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMC1HR5qKz18E37TrhwqHJEY7G+oVkTpRX/5RksHWe+JPmvtsXtgPEozxLsP63hj8iXlzA8KWE6aldAWH3U+jFeyX9Uc18K3U5xb6lf5JjW6CqcqhTnczaxafrK+23SJ1YjCuwA3L4clQaf1b68VZRIu1gsqe0n8cq0k06ehaWzNAgMBAAECgYEAi5LiK0REWz0BtctFgNqzZBhELz8idLjsAJugYPlLF2Y1EuDOuohiQnAqXj5Skxj4qqA84uvgN9ZZCaTsVfPemuc9UKv7Vgpbc6nc6uhDWhXb8MgebORYsMv/h2oO07AGJEiihRa7hyltJovEVrjSW2307bN++lQfMS9OdULeOoECQQDwBz+Rt3W/MLcYUUeEAsyXtqXX9UQDKUxBoaK1EYrlBY+Y5y++Pz3xhHJD3QAclrmKB+ffzuX0dlknrFq8eAmtAkEAzYfH3FDQA/He/tTKQMeP7+rmDHXMO16Xe1YqSg2Tz75MTRQbwUkMUtyX0X7+3yDkv0Xx2CfOBKqAH9K0Y3GToQJBAIHKiZdT6vm9b+RYXyGFGfiXrUn/uA01kaSTsJXUrJR201VM/cYUEHy+r8L+iAbtgdqft8SP7kyoikEns9Dh3+kCQHjv859lAfHASeoDBfu2MbEHtFQSoJkyoMoXOo0WjipInJciRO6n8BN17/N62bgrn84Y9ySsz2IZfYi8MB7vvuECQQDdzrosAsEqaNHTo+784HUP/2lvTvU/1OVAhnLa5iS7x7zKw8zth7D2+z8EBdaxbJefl0HdEb0rcEPc1fxbRvbw"))
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
