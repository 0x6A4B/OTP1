package dev._x6a4b.otp1.controller;

import dev._x6a4b.otp1.entity.Device;
import dev._x6a4b.otp1.entity.LogEntry;
import dev._x6a4b.otp1.service.DeviceLogService;
import dev._x6a4b.otp1.service.DeviceService;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private DeviceLogService deviceLogService;
    @Autowired
    private DeviceService deviceService;

    @GetMapping("/bydevice/{id}")
    public ResponseEntity<List<LogEntry>> getAllEntriesByDeviceId(@PathVariable long id, @RequestParam(required = false) Integer limit){
        System.out.println("get /api/log/bydevice/" + id);
        if (limit != null)
            return new ResponseEntity<>(deviceLogService.getLogEntriesWithLimits(id, limit).get(), HttpStatus.OK);
        return new ResponseEntity<>(deviceLogService.getLogEntries(id).get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogEntry> getLogEntry(@PathVariable long id){
        System.out.println("get logentry: " + id);
        try {
            return new ResponseEntity<>(deviceLogService.getLogById(id).get(), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/bydevice/{id}")
    public ResponseEntity<LogEntry> createLogEntry(@PathVariable long id, @RequestBody LogEntry logEntry){
        System.out.println("create logentry");
        Device device;
        try {
            device = deviceService.getDeviceById(id).get();
            //device.addLogEntry(logEntry);
            logEntry.setDevice(device);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("device: " + device.getName());
        System.out.println("user: " + getUsername());
        LogEntry createdEntry = deviceLogService.saveLogEntry(logEntry);
        return new ResponseEntity<LogEntry>(createdEntry, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LogEntry> deleteLogEntry(@PathVariable long id){
        System.out.println("delete logentry: " + id);
        try {
            LogEntry logEntry = deviceLogService.getLogById(id).get();
            deviceLogService.removeLogEntry(logEntry);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
