package io.github.thewilly.conductor.server.repositories.experimental

import io.github.thewilly.conductor.server.types.DeviceAction
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface DeviceActionsRepository : MongoRepository<DeviceAction, ObjectId>
