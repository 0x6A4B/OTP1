package dev._x6a4b.otp1.repository;

import dev._x6a4b.otp1.entity.DeviceShare;
import dev._x6a4b.otp1.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceShareRepository extends JpaRepository<DeviceShare, Long> {
    Optional<List<DeviceShare>> findByUserId(Long userid);
}
