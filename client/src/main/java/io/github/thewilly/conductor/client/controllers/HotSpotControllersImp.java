package io.github.thewilly.conductor.client.controllers;

import io.github.thewilly.conductor.client.security.RegistrationProtocol;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HotSpotControllersImp {

    @RequestMapping(value = "/api/register", method = RequestMethod.GET)
    public ResponseEntity<String> register() {
        return new RegistrationProtocol().init();
    }
}
