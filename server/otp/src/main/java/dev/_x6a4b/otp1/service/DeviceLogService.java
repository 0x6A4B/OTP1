package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.entity.Device;
import dev._x6a4b.otp1.entity.LogEntry;
import dev._x6a4b.otp1.repository.DeviceLogRepository;
import dev._x6a4b.otp1.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceLogService {
    private final DeviceLogRepository deviceLogRepository;

    @Autowired
    public DeviceLogService(DeviceRepository deviceRepository, DeviceLogRepository deviceLogRepository){
        this.deviceLogRepository = deviceLogRepository;
    }

    public Optional<LogEntry> getLogById(Long id){
        System.out.println("devicelogservice getlogbyid: " + id);
        return deviceLogRepository.findById(id);
    }

    public LogEntry saveLogEntry(LogEntry logEntry){
        System.out.println("devicelogservice savelogentry");
        return deviceLogRepository.saveAndFlush(logEntry);
        //return logEntry;
    }

    public Optional<List<LogEntry>> getLogEntries(Long deviceid){
        System.out.println("devicelogservice getlogs by device, id: " + deviceid);
        return deviceLogRepository.findByDeviceId(deviceid);
    }

    public void removeLogEntry(LogEntry logEntry){
        System.out.println("devicelogservice removelogentry: " + logEntry.getId());
        deviceLogRepository.delete(logEntry);
    }
}
