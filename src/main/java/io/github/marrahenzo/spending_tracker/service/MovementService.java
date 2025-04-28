package io.github.marrahenzo.spending_tracker.service;

import io.github.marrahenzo.spending_tracker.model.Movement;
import io.github.marrahenzo.spending_tracker.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementService {

    private final MovementRepository movementRepository;

    @Autowired
    public MovementService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public Optional<Movement> findById(Long id) {
        return movementRepository.findById(id);
    }

    public List<Movement> findByUserId(Long userId) {
        return movementRepository.findByUserId(userId);
    }

    public List<Movement> findAll() {
        return movementRepository.findAll();
    }

    public void deleteById(Long id) {
        movementRepository.deleteById(id);
    }

    public void save(Movement movement) {
        movementRepository.save(movement);
    }
}
