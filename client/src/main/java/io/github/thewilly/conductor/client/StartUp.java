package io.github.thewilly.conductor.client;

import io.github.thewilly.conductor.client.types.*;
import kong.unirest.Unirest;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;


@SpringBootApplication
public class StartUp {

    public static SDCard SD_CARD = new SDCard("");
    /*public static SimCard SIM =
            new SimCard(SD_CARD.getMemmoryMap().get("imsi"),
                    new KeyPair(SD_CARD.getMemmoryMap().get("public_key"),
                                SD_CARD.getMemmoryMap().get("public_key")));*/

    public static SimCard SIM =
            new SimCard("8944502802190670052","1234567891234567");

    public static HotSpot HOT_SPOT = new HotSpot(SIM);

    public static void main(String... args) throws IOException {
        SpringApplication.run(StartUp.class, args);

        JSONObject payload = new JSONObject();
        payload.put("imsi",HOT_SPOT.getSIM().getImsi());
        payload.put("ip","localhost:8080");

        StringEntity entity = new StringEntity(payload.toString(),
                ContentType.APPLICATION_FORM_URLENCODED);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://" + MasterServer.address + "/api/register");
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response);
    }
}
