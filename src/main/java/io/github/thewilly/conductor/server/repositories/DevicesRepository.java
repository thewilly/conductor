package io.github.thewilly.conductor.server.repositories;

import io.github.thewilly.conductor.server.types.Device;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DevicesRepository extends MongoRepository<Device, ObjectId> {

    /**
     *
     * @param token
     * @return
     */
    Device findByToken(String token);
}
