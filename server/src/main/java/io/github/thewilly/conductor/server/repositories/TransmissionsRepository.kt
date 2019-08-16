package io.github.thewilly.conductor.server.repositories

import io.github.thewilly.conductor.server.types.Transmission
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TransmissionsRepository : MongoRepository<Transmission, ObjectId>
