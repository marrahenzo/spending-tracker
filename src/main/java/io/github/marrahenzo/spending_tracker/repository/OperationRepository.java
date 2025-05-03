package io.github.marrahenzo.spending_tracker.repository;

import io.github.marrahenzo.spending_tracker.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByUserId(Long userId);
}
