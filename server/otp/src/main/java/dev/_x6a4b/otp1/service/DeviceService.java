package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.entity.Device;
import dev._x6a4b.otp1.entity.User;
import dev._x6a4b.otp1.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository){
        this.deviceRepository = deviceRepository;
    }


    public Optional<Device> getDeviceById(Long id){
        System.out.println("deviceservice.getdevicebyid: " + id);
        return deviceRepository.findById(id);
    }

    public Device saveDevice(Device device){
        return deviceRepository.saveAndFlush(device);
    }

    public List<Device> getDevices(User user){
        return deviceRepository.findByUserId(user.getId());  // sorting?
    }

    public void removeDevice(Device device){
        deviceRepository.delete(device);    // how to know this worked? no return type
    }
}
