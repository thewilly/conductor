package io.github.thewilly.conductor.server.types

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(val userName: String, val password: String, val isAdmin : Boolean) {

    @Id
    private val userId: ObjectId? = null

    var channelsWithAccess: List<Channel>? = null
    var devicesWithAccess: List<Device>? = null

    fun canAccessToChannel(channel: Channel): Boolean {
        return channelsWithAccess!!.contains(channel)
    }

    fun canAccessToDevice(device: Device): Boolean {
        return devicesWithAccess!!.contains(device)
    }
}
