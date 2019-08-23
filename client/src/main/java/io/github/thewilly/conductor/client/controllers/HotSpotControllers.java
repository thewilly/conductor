package io.github.thewilly.conductor.client.controllers;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public interface HotSpotControllers {

    ResponseEntity<JSONObject> info();

    ResponseEntity<JSONObject> changeFrequency(Map<String, Object> payload);
}
