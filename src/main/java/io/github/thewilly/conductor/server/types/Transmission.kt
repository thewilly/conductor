package io.github.thewilly.conductor.server.types

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import java.util.Date

@Document(collection = "transmissions")
class Transmission {

    @Id
    val transmissionId: ObjectId? = null
    val date: Date

    init {
        this.date = Date()
    }
}
