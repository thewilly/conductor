package io.github.thewilly.conductor.server.controllers.web;

import io.github.thewilly.conductor.server.types.Channel;
import io.github.thewilly.conductor.server.types.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DevicesController {

    @RequestMapping(value = "/devices/turnon", method = RequestMethod.POST)
    public void turnOn(Device device) {

    }

    @RequestMapping(value = "/devices/turnoff", method = RequestMethod.POST)
    public void turnOff(Device device) {

    }

    public void changeChannel(Device device, Channel channel) {

    }
}
