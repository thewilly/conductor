package io.github.thewilly.conductor.client.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/api")
public class RaspberryPiController {

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "Hi324";
    }
}
