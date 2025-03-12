package dev._x6a4b.otp1.controller;

import dev._x6a4b.otp1.entity.Device;
import dev._x6a4b.otp1.entity.DeviceShare;
import dev._x6a4b.otp1.entity.DeviceShareDTO;
import dev._x6a4b.otp1.entity.User;
import dev._x6a4b.otp1.service.DeviceService;
import dev._x6a4b.otp1.service.DeviceShareService;
import dev._x6a4b.otp1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/deviceshare")
public class DeviceShareController {

    @Autowired
    private DeviceShareService deviceShareService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;


    @GetMapping("")
    public ResponseEntity<List<DeviceShare>> getAllDeviceShares(Principal principal){
        System.out.println("get /api/deviceshare");
        User user = userService.getUser(principal.getName());
        System.out.println("userid: " + user.getId());

        return new ResponseEntity<>(deviceShareService.getDeviceByUserId(user.getId()).get(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<DeviceShare> createDeviceShare(@RequestBody DeviceShareDTO deviceShareDTO, Principal principal){
        System.out.println("createDeviceShare: " + deviceShareDTO.getUser().getId());
        System.out.println("createDeviceShare: " + deviceShareDTO.getDevice().getId());

        return new ResponseEntity<>(deviceShareService.saveDeviceShare(deviceShareDTO), HttpStatus.CREATED);
    }



}
