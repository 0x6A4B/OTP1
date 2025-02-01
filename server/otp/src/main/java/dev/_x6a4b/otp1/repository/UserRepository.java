package dev._x6a4b.otp1.repository;

import dev._x6a4b.otp1.entity.User;
import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{


    @Find
    Optional<User> findByUserName(String username);

/*
    @Find
    User find(long id);

 */
}
