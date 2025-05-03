package io.github.marrahenzo.spending_tracker.service;

import io.github.marrahenzo.spending_tracker.dto.CategoryRequest;
import io.github.marrahenzo.spending_tracker.model.Category;
import io.github.marrahenzo.spending_tracker.model.User;
import io.github.marrahenzo.spending_tracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> findByUserId(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public void save(CategoryRequest category, User user) {
        var newOperation = Category.builder()
                .name(category.getName())
                .user(user)
                .build();
        categoryRepository.save(newOperation);
    }
}
