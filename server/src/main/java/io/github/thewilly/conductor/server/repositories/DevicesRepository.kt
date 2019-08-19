package io.github.thewilly.conductor.server.repositories

import io.github.thewilly.conductor.server.types.Device
import io.github.thewilly.conductor.server.types.DeviceStatus
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface DevicesRepository : MongoRepository<Device, ObjectId> {

    fun findByImsi(imsi: String): Device?

    fun findByStatus(status: DeviceStatus): List<Device>?

    fun findByIsRegistered(isRegistered: Boolean): List<Device>?

}
