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

    // GET all devices shared with this user
    @GetMapping("")
    public ResponseEntity<List<DeviceShare>> getAllDeviceShares(Principal principal){
        System.out.println("get /api/deviceshare");
        User user = userService.getUserByName(principal.getName()).get();
        System.out.println("userid: " + user.getId() + " username: " + user.getUsername());

        return new ResponseEntity<>(deviceShareService.getDeviceSharesByUserId(user.getId()).get(), HttpStatus.OK);
    }

    // GET all devices shared BY this user
    @GetMapping("/myshares")
    public ResponseEntity<List<DeviceShare>> getMyDeviceShares(Principal principal){
        System.out.println("get /api/deviceshare/mysahres : " + principal.getName());
        return new ResponseEntity<>(deviceShareService.getDeviceSharesByDeviceOwnerName(principal.getName()), HttpStatus.OK);
    }

    // POST => create new device, only allowed for the owner of the device
    @PostMapping("")
    public ResponseEntity<DeviceShare> createDeviceShare(@RequestBody DeviceShareDTO deviceShareDTO, Principal principal){
        System.out.println("createDeviceShare: " + deviceShareDTO.getUser().getId());
        System.out.println("createDeviceShare: " + deviceShareDTO.getDevice().getId());

        try {
            Device device = deviceService.getDeviceById(deviceShareDTO.getDevice().getId()).get();
            // DTO should have filled the User based on ID or username
            if (principal.getName().equals(device.getUser().getUsername())) {
                return new ResponseEntity<>(deviceShareService.saveDeviceShare(deviceShareDTO), HttpStatus.CREATED);
            } else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET all device shares for this device: used by the owner of the device
    @GetMapping("/{id}")
    public ResponseEntity<List<DeviceShare>> getDeviceShare(@PathVariable Long id, Principal principal){
        System.out.println("get /api/deviceshare/{id}:" + id);
        Device device = deviceService.getDeviceById(id).get();
        // Let's check the user querying is the owner
        if (principal.getName().equals(device.getUser().getUsername())) {
            return new ResponseEntity<>(deviceShareService.getDeviceSharesByDeviceId(id).get(), HttpStatus.OK);
        }
        // User has no rights to these shares
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    // DELETE device share, allowed for the owner of the device and the owner of the device share
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDeviceShare(@PathVariable Long id, Principal principal){
        System.out.println("deleteDeviceShare: " + id);

        // TODO: this should be implemented EVERYWHERE!!!!
        //  Exception handling is completely disregarded!! :(
        try {
            DeviceShare deviceShare = deviceShareService.getDeviceShareById(id).get();
            Device device = deviceService.getDeviceById(deviceShare.getDevice().getId()).get();

            // Allowed if queried by device owner OR device share owner
            if (principal.getName().equals(device.getUser().getUsername())
                    || principal.getName().equals(deviceShare.getUser().getUsername()) ) {
                deviceShareService.deleteDeviceShare(deviceShare);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PATCH device share, only device owner can set privilege, only share owner can change description
    // No other changes should be allowed
    @PatchMapping
    public ResponseEntity<DeviceShare> updateDeviceShare(@RequestBody DeviceShareDTO deviceShareDTO, Principal principal){
        System.out.println("updateDeviceShare: " + deviceShareDTO.getId());
        DeviceShare deviceShare = deviceShareService.getDeviceShareById(deviceShareDTO.getId()).get();

        try{
            // Query from share owner => can only set description
            if (principal.getName().equals(deviceShareDTO.getUser().getUsername())) {
                if (!deviceShare.getDescription().equals(deviceShareDTO.getDescription())) {
                    deviceShare.setDescription(deviceShareDTO.getDescription());
                    deviceShare = deviceShareService.updateDeviceShare(deviceShare);
                }
            } else  // Query from the device owner
                if (principal.getName().equals(deviceShareDTO.getDevice().getUser().getUsername())) {
                    deviceShare.setPrivilege(deviceShareDTO.getPrivilege());
                    deviceShare = deviceShareService.updateDeviceShare(deviceShare);
            } else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            // We return 200 and the new object. We do not need to return and could do 204 instead..
            return new ResponseEntity<>(deviceShare, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
