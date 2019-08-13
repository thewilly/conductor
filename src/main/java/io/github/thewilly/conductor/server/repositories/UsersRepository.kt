package io.github.thewilly.conductor.server.repositories

import io.github.thewilly.conductor.server.types.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UsersRepository : MongoRepository<User, ObjectId> {

    fun findByUserName(userName: String): User

}
