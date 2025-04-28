package io.github.marrahenzo.spending_tracker.controller;

import io.github.marrahenzo.spending_tracker.dto.MovementResponse;
import io.github.marrahenzo.spending_tracker.model.Movement;
import io.github.marrahenzo.spending_tracker.service.MovementService;
import io.github.marrahenzo.spending_tracker.util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping("/movement")
    public List<MovementResponse> getMovements(HttpServletRequest request) {
        var session = request.getSession(false);
        var userId = (long) session.getAttribute(Constants.SESSION_USER_ID);
        return movementService.findByUserId(userId).stream().map(MovementResponse::fromMovement).toList();
    }

    @GetMapping("/movement/{id}")
    public MovementResponse getMovement(@PathVariable Long id) {
        var movement = movementService.findById(id);
        if (movement.isEmpty())
            throw new IllegalArgumentException("The movement doesn't exist");
        return MovementResponse.fromMovement(movement.get());
    }

    @PostMapping("/movement")
    public Movement saveMovement(@RequestBody Movement movement) {
        movementService.save(movement);
        return movement;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleNotFound(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
