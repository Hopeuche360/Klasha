package com.klasha.repositories;

import com.klasha.models.Delivery;
import com.klasha.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findById(Long id);
    Optional<Delivery> findByIdAndUser(Long id, User user);
}
