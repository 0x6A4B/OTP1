package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.entity.Device;
import dev._x6a4b.otp1.entity.User;
import dev._x6a4b.otp1.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<Device> getDeviceByUuid(UUID uuid){
        System.out.println("Get device by UUID: " + uuid.toString());
        return deviceRepository.findByUuid(uuid);
    }

    public Device saveDevice(Device device){
        device.setRegistered(new Date());
        device.setUser(userService.getUserByName(getUsername()).get());

        return deviceRepository.saveAndFlush(device);
    }

    public Optional<List<Device>> getDevices(String username){
        System.out.println("detdevices by username: " + username);
        User user = userService.getUserByName(username).get();
        return deviceRepository.findByUserId(user.getId());  // sorting?
    }
    //findByOrderByRegisteredDesc

    public Optional<List<Device>> getDevicesWithLimit(String username, int limit){
        System.out.println("detdevices by username with limit: " + username + " and limit: " + limit);
        User user = userService.getUserByName(username).get();
        return deviceRepository.findByUserIdOrderByRegisteredDesc(user.getId(), Limit.of(limit));
    }


    public void removeDevice(Device device){
        deviceRepository.delete(device);
    }


    private String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
