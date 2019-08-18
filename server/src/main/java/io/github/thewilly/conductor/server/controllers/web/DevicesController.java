package io.github.thewilly.conductor.server.controllers.web;

import io.github.thewilly.conductor.server.services.DevicesService;
import io.github.thewilly.conductor.server.types.Channel;
import io.github.thewilly.conductor.server.types.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class DevicesController {

    @Autowired
    private DevicesService devicesService;

    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    public String getListOpen(Model model) {

        List<Device> incidents = devicesService.getAllDevices();

        model.addAttribute( "devices", incidents );
        return "devices/list";
    }
}
