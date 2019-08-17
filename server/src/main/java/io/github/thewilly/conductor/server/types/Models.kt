package io.github.thewilly.conductor.server.types

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*

@Document(collection = "transmissions")
data class Transmission(@Id val transmissionId: String? = null,
                        val date: Date = Date.from(Instant.now()))

@Document(collection = "channels")
data class Channel(@Id val channelId: String? = null,
                   var name: String,
                   var freq: String,
                   var ctcss: String)

@Document(collection = "devices")
data class Device(@Id val deviceId: String? = null,
                  val mac: String,
                  var ip: String,
                  var channel: Channel ?= null,
                  var location: String,
                  var key: String,
                  var isOn: Boolean = false)


/*   EXPERIMENTAL FEATURES   */


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