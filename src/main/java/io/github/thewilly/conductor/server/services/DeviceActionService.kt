package io.github.thewilly.conductor.server.services

import io.github.thewilly.conductor.server.repositories.DeviceActionsRepository
import io.github.thewilly.conductor.server.types.DeviceAction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeviceActionService {

    @Autowired
    private val deviceActionRepo: DeviceActionsRepository? = null

    fun recordAction(deviceMac: String, actionMessage: String) {
        val (deviceActionId, deviceMac1, action1) = DeviceAction(deviceMac = deviceMac, action = actionMessage)
    }
}
