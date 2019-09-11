package io.github.thewilly.conductor.client.controllers;

import org.json.JSONObject;
import java.util.Map;

public interface HotSpotControllers {

    void info();

    void changeFrequency(Map<String, Object> payload);
}
