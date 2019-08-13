package io.github.thewilly.conductor.server.services;

import io.github.thewilly.conductor.server.repositories.DevicesRepository;
import io.github.thewilly.conductor.server.types.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevicesService {

    @Autowired
    private DevicesRepository devicesRepo;

    public Device register(String deviceName, String location) {
        Device device = new Device(deviceName, location);
        devicesRepo.save(device);
        return device;
    }

    public Device authenticate(String deviceToken) {
        return devicesRepo.findByToken(deviceToken);
    }
}
