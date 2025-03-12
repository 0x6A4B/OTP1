package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.entity.Device;
import dev._x6a4b.otp1.entity.DeviceShare;
import dev._x6a4b.otp1.entity.DeviceShareDTO;
import dev._x6a4b.otp1.entity.User;
import dev._x6a4b.otp1.repository.DeviceShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceShareService {
    private final DeviceService deviceService;
    private final UserService userService;
    private final DeviceShareRepository deviceShareRepository;

    @Autowired
    public DeviceShareService(DeviceService deviceService, UserService userService, DeviceShareRepository deviceShareRepository) {
        this.deviceService = deviceService;
        this.userService = userService;
        this.deviceShareRepository = deviceShareRepository;
    }

    public Optional<List<DeviceShare>> getDeviceByUserId(Long id){
        System.out.println("deviceshareservice.getdevicesharebyuserid: " + id);
        return deviceShareRepository.findByUserId(id);
    }

    public DeviceShare saveDeviceShare(DeviceShareDTO deviceShareDTO){
        System.out.println("deviceshareservice.createdeviceshare");
        System.out.println("DEBUG:");
        System.out.println(deviceShareDTO.getDevice().getId());
        System.out.println(deviceShareDTO.getUser().getId());

        User user = userService.getUserById(deviceShareDTO.getUser().getId()).get();
        System.out.println("user: " + user.getUsername());

        Device device = deviceService.getDeviceById(deviceShareDTO.getDevice().getId()).get();
        System.out.println("device: " + device.getName());


        DeviceShare deviceShare = new DeviceShare(
                device,
                user,
                new Date(),
                deviceShareDTO.getPrivilege(),
                deviceShareDTO.getDescription()
        );
        System.out.println("deviceshareservice.createdeviceshare" + deviceShare.getUser().getId() + " " + deviceShare.getDevice().getId());

        return deviceShareRepository.saveAndFlush(deviceShare);
    }
}
