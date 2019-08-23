package io.github.thewilly.conductor.client;

import io.github.thewilly.conductor.client.types.*;
import kong.unirest.Unirest;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartUp {

    public static SDCard SD_CARD = new SDCard("");
    /*public static SimCard SIM =
            new SimCard(SD_CARD.getMemmoryMap().get("imsi"),
                    new KeyPair(SD_CARD.getMemmoryMap().get("public_key"),
                                SD_CARD.getMemmoryMap().get("public_key")));*/

    public static SimCard SIM =
            new SimCard("8944502802190670052",
                    new KeyPair("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVOof427+tONEt5v/QX1N9h912XVV0pOWbQAON8LWH/FQZOGNh1pjsydOljKFeGWdEhWzISix2r4aI+84aBAA0FXPqqPxfwKFHsgXCRtMNOz6ndH6Neyea7/hwN+dpWRJqRZkb4kAOCvjfemWka1xrJbQWhMN2lLG75Y8cIvQTeQIDAQAB", "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJU6h/jbv6040S3m/9BfU32H3XZdVXSk5ZtAA43wtYf8VBk4Y2HWmOzJ06WMoV4ZZ0SFbMhKLHavhoj7zhoEADQVc+qo/F/AoUeyBcJG0w07Pqd0fo17J5rv+HA352lZEmpFmRviQA4K+N96ZaRrXGsltBaEw3aUsbvljxwi9BN5AgMBAAECgYB9lPQ4iTWVIOtoJa3CPXaVfXDx2zm/H8XaENTs7lTWS5K6Zqm7/ExVeMOT3fUuq1IsZ9xT8ZfZiX0+ainX2uYPpb1QqeAq+CBRyPd8h3VLncOGYzTFEZmODXvtwgDdojGUHRPaDjmvzUSkcU8Tq4l9XFHRhhYgoAnjchk8s6WuwQJBAMY1SKa/CuydLzcgmOpsWsEXLxHs0AErWENLEgvP6Cvti/uIcH4uY3UpgqcaQx7O7dX2JjkE1bGV53hxpt3YVg8CQQDAvU2uiUjVJuGNpEzwoW2w97JuU3cwnZIurFmmyb+L/LwO6UZNn3O+VuSPBrvbKNDV33F8DqhcCqhnfHOmiUX3AkEAo8K0Yuhm2zXHI9Hv5YdeuDgycKnq9XxdcUPeBhphpaGfTVPeNbKYA94nD28YU6T+RhAYZnXDJPVIiG/GQXaTKwJAJ63RNEFnERq0DKjqfg99uZUE0tm0C/okgh9RRZj7KiKZxKgZpRlShrj0zM0iF3hLpfweLOAHoQtAqC1i0cIo+QJAX8Am7v0Ur1bU+PTC2yUR+St3C2UCFYbESBLWYADR8r9SYnBM27q1NA5L56X6F6F1InM0Sme8K2wHP1c7nYjC9g=="));

    public static HotSpot HOT_SPOT = new HotSpot(SIM);

    public static void main(String... args) {
        SpringApplication.run(StartUp.class, args);

        JSONObject payload = new JSONObject();
        payload.put("imsi",HOT_SPOT.getSIM().getImsi());
        payload.put("ip","192.168.1.134");
        Unirest.post("http://" + MasterServer.address + ":8080" + "/api/register").body(payload).asJson();
    }
}
