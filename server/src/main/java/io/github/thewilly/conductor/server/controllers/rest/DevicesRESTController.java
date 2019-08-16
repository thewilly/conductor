package io.github.thewilly.conductor.server.controllers.rest;

import io.github.thewilly.conductor.server.services.DeviceActionService;
import io.github.thewilly.conductor.server.services.DevicesService;
import io.github.thewilly.conductor.server.types.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DevicesRESTController {

    @Autowired
    DevicesService devicesService;

    @Autowired
    DeviceActionService devicesActionService;

    @RequestMapping(value = "/devices/register", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public Device register(@RequestBody Map<String, Object> payload){
        devicesActionService.recordAction(payload.get("mac").toString(), "Device registered.");
        return this.devicesService.register(
                payload.get("deviceName").toString(),
                payload.get("deviceLocation").toString(),
                payload.get("deviceIP").toString(),
                payload.get("mac").toString());
    }

    @RequestMapping(value = "/devices/register", method = RequestMethod.DELETE, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public Device unregister(@RequestBody Map<String, Object> payload) {
        devicesActionService.recordAction(payload.get("deviceToken").toString(), "Device (un)registered.");
        return this.devicesService.unregister(
                payload.get("deviceToken").toString());
    }

}
