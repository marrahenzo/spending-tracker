package io.github.marrahenzo.spending_tracker.service;

import io.github.marrahenzo.spending_tracker.dto.OperationRequest;
import io.github.marrahenzo.spending_tracker.model.Category;
import io.github.marrahenzo.spending_tracker.model.Currency;
import io.github.marrahenzo.spending_tracker.model.Operation;
import io.github.marrahenzo.spending_tracker.model.User;
import io.github.marrahenzo.spending_tracker.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    private final OperationRepository operationRepository;
    private final CategoryService categoryService;

    @Autowired
    public OperationService(OperationRepository operationRepository, CategoryService categoryService) {
        this.operationRepository = operationRepository;
        this.categoryService = categoryService;
    }

    public Optional<Operation> findById(Long id) {
        return operationRepository.findById(id);
    }

    @Transactional
    public List<Operation> findByUserId(Long userId) {
        return operationRepository.findByUserId(userId);
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    public void deleteById(Long id) {
        operationRepository.deleteById(id);
    }

    public void save(OperationRequest operation, User user) {
        Optional<Category> category = Optional.empty();
        if (operation.getCategory() != null)
            category = categoryService.findById(operation.getCategory());
        var newOperation = Operation.builder()
                .id(operation.getId())
                .description(operation.getDescription())
                .amount(new BigDecimal(operation.getAmount()))
                .currency(Currency.valueOf(operation.getCurrency()))
                .category(category.orElse(null))
                .date(LocalDateTime.now())
                .user(user)
                .build();
        operationRepository.save(newOperation);
    }
}
