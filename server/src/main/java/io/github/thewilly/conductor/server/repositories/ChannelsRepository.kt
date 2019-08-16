package io.github.thewilly.conductor.server.repositories

import io.github.thewilly.conductor.server.types.Channel
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ChannelsRepository : MongoRepository<Channel, ObjectId>
