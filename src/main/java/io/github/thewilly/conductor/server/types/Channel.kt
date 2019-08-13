package io.github.thewilly.conductor.server.types

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "channels")
data class Channel(val channelName: String) {

    @Id
    private val channelId: ObjectId? = null
}
