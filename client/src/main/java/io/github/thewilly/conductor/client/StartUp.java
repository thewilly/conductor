package io.github.thewilly.conductor.client;

import io.github.thewilly.conductor.client.types.KeyPair;
import io.github.thewilly.conductor.client.types.SDCard;
import io.github.thewilly.conductor.client.types.SimCard;

public class StartUp {

    public static SDCard SD_CARD = new SDCard("");
    public static SimCard SIM =
            new SimCard(SD_CARD.getMemoryMap().get("imsi"),
                    new KeyPair(SD_CARD.getMemoryMap().get("public_key"),
                                SD_CARD.getMemoryMap().get("public_key")));


}
