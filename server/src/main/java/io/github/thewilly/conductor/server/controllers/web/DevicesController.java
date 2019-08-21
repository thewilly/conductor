package io.github.thewilly.conductor.server.controllers.web;

import io.github.thewilly.conductor.server.services.DevicesService;
import io.github.thewilly.conductor.server.types.Channel;
import io.github.thewilly.conductor.server.types.ChannelInfo;
import io.github.thewilly.conductor.server.types.Device;

import io.github.thewilly.conductor.server.types.DeviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class DevicesController {

    @Autowired
    private DevicesService devicesService;

    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    public String getListOpen(Model model, @Nullable @CookieValue("bmnUserEmail") String sessionCookie) {
        if (sessionCookie == null)
            return "redirect:/login";

        List<Device> incidents = devicesService.getAllDevices();

        model.addAttribute( "devices", incidents );
        return "devices/list";
    }

    @RequestMapping(value = "/devices/new", method = RequestMethod.GET)
    public String createDevice(@Nullable @CookieValue("bmnUserEmail") String sessionCookie) {
        if (sessionCookie == null)
            return "redirect:/login";

        return "devices/new";
    }

    @RequestMapping(value = "/devices/new", method = RequestMethod.POST)
    public String createDevice(Model model, @ModelAttribute("DeviceInfo") DeviceInfo deviceInfo,
                               BindingResult result, HttpServletResponse response, @CookieValue("bmnUserEmail") String sessionCookie) {
        if (sessionCookie == null)
            return "redirect:/login";

        devicesService.create(deviceInfo.getImsi(), deviceInfo.getPublicKey());
        return "redirect:/devices";
    }

    @RequestMapping(value = "/devices/remove/{id}", method = RequestMethod.GET)
    public String remove(Model model, @PathVariable String id, @Nullable @CookieValue("bmnUserEmail") String sessionCookie) {
        if (sessionCookie == null)
            return "redirect:/login";

        devicesService.remove(id);
        return "redirect:/devices";
    }
}
