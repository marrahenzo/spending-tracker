package io.github.marrahenzo.spending_tracker.controller;

import io.github.marrahenzo.spending_tracker.annotation.CurrentUser;
import io.github.marrahenzo.spending_tracker.dto.OperationRequest;
import io.github.marrahenzo.spending_tracker.dto.OperationResponse;
import io.github.marrahenzo.spending_tracker.dto.SuccessDTO;
import io.github.marrahenzo.spending_tracker.model.User;
import io.github.marrahenzo.spending_tracker.model.entityview.AmountPerCategoryView;
import io.github.marrahenzo.spending_tracker.service.OperationService;
import io.github.marrahenzo.spending_tracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public List<OperationResponse> getOperations(@CurrentUser User user) {
        return operationService.findByUserId(user.getId()).stream().map(OperationResponse::fromOperation).toList();
    }

    @GetMapping("/operation/{id}")
    public OperationResponse getOperation(@PathVariable Long id) {
        var movement = operationService.findById(id);
        if (movement.isEmpty())
            throw new IllegalArgumentException("The operation doesn't exist");
        return OperationResponse.fromOperation(movement.get());
    }

    @PostMapping("/operation")
    public ResponseEntity<SuccessDTO> saveOperation(@RequestBody OperationRequest operation, @CurrentUser User user) {
        operationService.save(operation, user);
        return ResponseEntity.ok().body(SuccessDTO.builder().message("Operation created successfully").build());
    }

    @DeleteMapping("/operation/{id}")
    public ResponseEntity<SuccessDTO> saveOperation(@PathVariable Long id, @CurrentUser User user) {
        var operation = this.operationService.findById(id).orElseThrow(() -> new IllegalArgumentException("Operation not found"));
        if (operation.getUser().getId() != user.getId())
            throw new IllegalArgumentException("The operation does not belong to the current user");
        operationService.deleteById(id);
        return ResponseEntity.ok().body(SuccessDTO.builder().message("Operation deleted successfully").build());
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(@CurrentUser User user) {
        return ResponseEntity.ok().body(operationService.getBalance(user));
    }

    @GetMapping("/amounts-per-category")
    public ResponseEntity<List<AmountPerCategoryView>> getAmountPerCategory(@CurrentUser User user) {
        return ResponseEntity.ok().body(operationService.getAmountPerCategory(user));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleNotFound(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
