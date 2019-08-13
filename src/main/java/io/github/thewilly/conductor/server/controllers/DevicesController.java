package io.github.thewilly.conductor.server.controllers;

import io.github.thewilly.conductor.server.services.DevicesService;
import io.github.thewilly.conductor.server.types.Device;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DevicesController {

    @Autowired
    DevicesService devicesService;

    @RequestMapping(value = "/devices/register", method = RequestMethod.GET)
    public Device register(@RequestParam @NotNull String deviceName, @RequestParam @NotNull String deviceLocation) {
        return this.devicesService.register(deviceName, deviceLocation);
    }

}
