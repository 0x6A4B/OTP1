package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.entity.Device;
import dev._x6a4b.otp1.entity.User;
import dev._x6a4b.otp1.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserService userService;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UserService userService){
        this.deviceRepository = deviceRepository;
        this.userService = userService;
    }


    public Optional<Device> getDeviceById(Long id){
        System.out.println("deviceservice.getdevicebyid: " + id);
        return deviceRepository.findById(id);
    }

    public Device saveDevice(Device device){
        device.setRegistered(new Date());
        device.setUser(userService.getUserByName(getUsername()).get());

        return deviceRepository.saveAndFlush(device);
    }

    public List<Device> getDevices(String username){
        User user = userService.getUserByName(username).get();
        return deviceRepository.findByUserId(user.getId());  // sorting?
    }

    public void removeDevice(Device device){
        deviceRepository.delete(device);
    }


    private String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
