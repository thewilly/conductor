package io.github.thewilly.conductor.server.types

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*

@Document(collection = "transmissions")
data class Transmission(@Id val transmissionId: String? = null, val transmissionDate: Date = Date.from(Instant.now()))

@Document(collection = "channels")
data class Channel(@Id val channelId: String? = null, val channelName: String, var frequency: String, var ctcssFrequency: String)

@Document(collection = "devices")
data class Device(@Id val deviceId: String? = null, var isOff: Boolean = true, val deviceName: String, val location: String, val deviceMac: String, var deviceIP: String = "0.0.0.0",var listenningChannel: Channel? = null, val deviceToken: String? = Token.builder(), val registeredDate: Date = Date.from(Instant.now()), var lastAuthDate: Date = Date.from(Instant.now()))

@Document(collection = "device-actions")
data class DeviceAction(@Id val deviceActionId: String? = null, val deviceMac: String, val action: String)

@Document(collection = "users")
data class User(@Id val userId: String? = null, val userName: String, val password: String, val isAdmin : Boolean, var channelsWithAccess: List<Channel>? = null,  var devicesWithAccess: List<Device>? = null) {

    fun canAccessToChannel(channel: Channel): Boolean {
        return channelsWithAccess!!.contains(channel)
    }

    fun canAccessToDevice(device: Device): Boolean {
        return devicesWithAccess!!.contains(device)
    }
}