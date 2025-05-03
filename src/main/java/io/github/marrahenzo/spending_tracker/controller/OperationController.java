package io.github.marrahenzo.spending_tracker.controller;

import io.github.marrahenzo.spending_tracker.dto.OperationRequest;
import io.github.marrahenzo.spending_tracker.dto.OperationResponse;
import io.github.marrahenzo.spending_tracker.dto.SuccessDTO;
import io.github.marrahenzo.spending_tracker.service.OperationService;
import io.github.marrahenzo.spending_tracker.service.UserService;
import io.github.marrahenzo.spending_tracker.util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OperationController {

    private final OperationService operationService;
    private final UserService userService;

    public OperationController(OperationService operationService, UserService userService) {
        this.operationService = operationService;
        this.userService = userService;
    }

    @GetMapping("/operation")
    public List<OperationResponse> getOperations(HttpServletRequest request) {
        var session = request.getSession(false);
        var userId = (long) session.getAttribute(Constants.SESSION_USER_ID);
        return operationService.findByUserId(userId).stream().map(OperationResponse::fromOperation).toList();
    }

    @GetMapping("/operation/{id}")
    public OperationResponse getOperation(@PathVariable Long id) {
        var movement = operationService.findById(id);
        if (movement.isEmpty())
            throw new IllegalArgumentException("The operation doesn't exist");
        return OperationResponse.fromOperation(movement.get());
    }

    @PostMapping("/operation")
    public ResponseEntity<SuccessDTO> saveOperation(@RequestBody OperationRequest operation, HttpServletRequest request) {
        var session = request.getSession(false);
        var userId = (long) session.getAttribute(Constants.SESSION_USER_ID);
        var user = this.userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        operationService.save(operation, user);
        return ResponseEntity.ok().body(SuccessDTO.builder().message("Operation created successfully").build());
    }

    @DeleteMapping("/operation/{id}")
    public ResponseEntity<SuccessDTO> saveOperation(@PathVariable Long id, HttpServletRequest request) {
        var session = request.getSession(false);
        var userId = (long) session.getAttribute(Constants.SESSION_USER_ID);
        var user = this.userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        var operation = this.operationService.findById(id).orElseThrow(() -> new IllegalArgumentException("Operation not found"));
        if (operation.getUser().getId() != user.getId())
            throw new IllegalArgumentException("The operation does not belong to the current user");
        operationService.deleteById(id);
        return ResponseEntity.ok().body(SuccessDTO.builder().message("Operation deleted successfully").build());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleNotFound(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
