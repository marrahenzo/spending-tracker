package io.github.marrahenzo.spending_tracker.repository;

import io.github.marrahenzo.spending_tracker.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByUserId(Long userId);
}
