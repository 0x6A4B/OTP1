package dev._x6a4b.otp1.controller;

import dev._x6a4b.otp1.entity.Device;
import dev._x6a4b.otp1.entity.User;
import dev._x6a4b.otp1.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // let's solve this with authorization? and use get..
    /*
    @PostMapping("/")
    public ResponseEntity<> getAllDevicesByUser(@RequestBody User user){
        return ResponseEntity.ok(deviceService.getDevices(user));
    }
     */

    @GetMapping("")
    public List<Device> getAllDevices(){
        System.out.println("get /api/device");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return deviceService.getDevices(username);
        //return new ArrayList<Device>();
    }

    @PostMapping("")
    public ResponseEntity<Device> createDevice(@RequestBody Device device){
        System.out.println("createdevice");
        System.out.println("device: " + device.getName());
        return new ResponseEntity<Device>(device, HttpStatus.CREATED);
    }



}
