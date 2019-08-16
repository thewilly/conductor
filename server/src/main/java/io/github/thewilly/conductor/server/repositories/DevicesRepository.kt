package io.github.thewilly.conductor.server.repositories

import io.github.thewilly.conductor.server.types.Device
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface DevicesRepository : MongoRepository<Device, ObjectId> {

    fun findByDeviceToken(token: String): Device

    fun findByDeviceMac(mac: String): Device
}
