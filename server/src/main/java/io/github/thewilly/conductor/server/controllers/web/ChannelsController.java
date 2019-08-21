package io.github.thewilly.conductor.server.controllers.web;

import io.github.thewilly.conductor.server.services.ChannelsService;
import io.github.thewilly.conductor.server.types.Channel;
import io.github.thewilly.conductor.server.types.ChannelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ChannelsController {

    @Autowired
    private ChannelsService channelsService;

    @RequestMapping( value = "/channels", method = RequestMethod.GET )
    public String getChannels(Model model, @Nullable @CookieValue("bmnUserEmail") String sessionCookie) {
        if (sessionCookie == null)
            return "redirect:/login";

        List<Channel> channels = channelsService.getAllChannels();
        model.addAttribute( "channels", channels );

        return "channels/list";
    }

    @RequestMapping( value = "/channels/new", method = RequestMethod.GET)
    public String addChannel(@Nullable @CookieValue("bmnUserEmail") String sessionCookie) {
        if (sessionCookie == null)
            return "redirect:/login";

        return "channels/new";
    }
    @RequestMapping( value = "/channels/new", method = RequestMethod.POST)
    public String addChannel(Model model, @ModelAttribute("ChannelInfo") ChannelInfo channelInfo,
                             BindingResult result, HttpServletResponse response, @CookieValue("bmnUserEmail") String sessionCookie) {
        if (sessionCookie == null)
            return "redirect:/login";
        channelsService.create(channelInfo.getName(), channelInfo.getFreq(), channelInfo.getCtcss());
        return "redirect:/channels";
    }

    @RequestMapping( value = "/channels/remove/{id}", method = RequestMethod.GET)
    public String remove(Model model, @PathVariable String id, @Nullable @CookieValue("bmnUserEmail") String sessionCookie) {
        if (sessionCookie == null)
            return "redirect:/login";

        channelsService.remove(id);
        return "redirect:/channels";
    }

}
