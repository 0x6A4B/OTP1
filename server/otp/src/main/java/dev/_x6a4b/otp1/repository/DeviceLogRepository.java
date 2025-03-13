package dev._x6a4b.otp1.repository;

import dev._x6a4b.otp1.entity.LogEntry;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceLogRepository  extends JpaRepository<LogEntry, Long> {

    Optional<List<LogEntry>> findByDeviceId(Long deviceid);

    Optional<List<LogEntry>> findByDeviceIdOrderByDateDesc(Long deviceid, Limit limits);

    @Transactional
    void deleteByDeviceId(Long deviceid);   // for deleting all logs if device is deleted?
    // test this ^
}
