package io.github.thewilly.conductor.server.types

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "devices")
data class Device(val deviceName: String, val location: String) {

    @Id
    private val deviceId: ObjectId? = null
    private val listenningChannel: Channel? = null
    private val deviceToken = Token.builder()
}
