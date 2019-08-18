package io.github.thewilly.conductor.server.controllers.rest;

import io.github.thewilly.conductor.server.services.experimental.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UsersRESTController {

    @Autowired
    UsersService usersService;

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public void addUser(@RequestBody Map<String, Object> payload) {
        usersService.addUser(payload.get("email").toString(), payload.get("password").toString());
    }
}
