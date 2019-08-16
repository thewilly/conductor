package io.github.thewilly.conductor.server.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DevicesController {

    @RequestMapping(value = "/devices/turnon", method = RequestMethod.POST)
    public void turnOn() {
        
    }
}
