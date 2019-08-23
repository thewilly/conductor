package io.github.thewilly.conductor.client.controllers;

import io.github.thewilly.conductor.client.security.RegistrationProtocol;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HotSpotControllersImp {

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> register(@RequestBody Map<String, Object> payload) {

        return new RegistrationProtocol(payload.get("secret").toString()).init();
    }
}
