package dev._x6a4b.otp1.repository;

import dev._x6a4b.otp1.entity.User;
import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Find
    User findByName(String username);

    @Find
    User find(long id);
}
