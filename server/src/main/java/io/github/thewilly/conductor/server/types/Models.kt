package io.github.thewilly.conductor.server.types

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.awt.geom.Point2D
import java.time.Instant
import java.util.*
import kotlin.collections.HashMap

@Document(collection = "transmissions")
data class Transmission(@Id val transmissionId: String? = null,
                        val date: Date = Date.from(Instant.now()))

@Document(collection = "channels")
data class Channel(@Id val channelId: String? = null,
                   var name: String,
                   var freq: String,
                   var ctcss: String,
                   var selectedOnScreen: Boolean = false,
                   var attributes: Map<String, Object> = HashMap<String, Object>())

@Document(collection = "devices")
data class Device(@Id val deviceId: String? = null,
                  val imsi: String,
                  val secret: String = RandomStringUtils.randomAlphanumeric(16),
                  var ip: String = "0.0.0.0",
                  var channel: Channel ?= null,
                  var location: Point2D.Float = Point2D.Float(),
                  var isRegistered: Boolean = false,
                  var status: DeviceStatus = DeviceStatus.OFF)

enum class DeviceStatus {
    STAND_BY,
    TX,
    RX,
    UPDATING,
    OFF
}


/*   EXPERIMENTAL FEATURES   */


@Document(collection = "device-actions")
data class DeviceAction(@Id val deviceActionId: String? = null, val deviceMac: String, val action: String)

@Document(collection = "users")
data class User(@Id val userId: String? = null, val email: String, val password: String)

data class LoginInfo(val email: String, val password: String)
data class ChannelInfo(val name: String, val freq: String, val ctcss: String)
data class DeviceInfo(val imsi: String, val publicKey: String)