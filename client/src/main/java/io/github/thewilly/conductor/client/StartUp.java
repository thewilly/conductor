package io.github.thewilly.conductor.client;

import io.github.thewilly.conductor.client.hardware.RaspberryPi;
import io.github.thewilly.conductor.client.hardware.RaspberryPiOperations;
import io.micronaut.runtime.Micronaut;

import java.io.IOException;
import java.util.Scanner;

public class StartUp {

    public static RaspberryPiOperations rPi = new RaspberryPi();

    public static void main(String... args) throws IOException {

        Scanner in = new Scanner(System.in);

        if(rPi.getConfiguration().getProperty("configured") == null) {
            System.out.print("IMSI: ");
            String imsi = in.nextLine();
            System.out.print("\nMaster server IP: ");
            String ip = in.nextLine();

            rPi.getConfiguration().setProperty("imsi", imsi);
            rPi.getConfiguration().setProperty("masterIP", ip);
        }

        //Micronaut.run(RaspberryPi.class);
    }
}
