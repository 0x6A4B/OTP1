package dev._x6a4b.otp1.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import dev._x6a4b.otp1.entity.Device;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


// https://www.bezkoder.com/jpa-one-to-many/    excellent source

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<List<Device>> findByUserId(Long userId);

    Optional<Device> findByUuid(UUID uuid);

    //@Query("SELECT d FROM Device d ORDER BY d.registered DESC LIMIT ")
    Optional<List<Device>> findByUserIdOrderByRegisteredDesc(Long userId, Limit limits);

    @Transactional
    void deleteByUserId(Long userId);   // for deleting all devices if user is deleted?
}
