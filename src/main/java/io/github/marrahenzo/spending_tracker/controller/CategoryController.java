package io.github.marrahenzo.spending_tracker.controller;

import io.github.marrahenzo.spending_tracker.dto.CategoryRequest;
import io.github.marrahenzo.spending_tracker.dto.CategoryResponse;
import io.github.marrahenzo.spending_tracker.dto.SuccessDTO;
import io.github.marrahenzo.spending_tracker.service.CategoryService;
import io.github.marrahenzo.spending_tracker.service.UserService;
import io.github.marrahenzo.spending_tracker.util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/category")
    public List<CategoryResponse> getCategories(HttpServletRequest request) {
        var session = request.getSession(false);
        var userId = (long) session.getAttribute(Constants.SESSION_USER_ID);
        return categoryService.findByUserId(userId).stream().map(CategoryResponse::fromCategory).toList();
    }

    @GetMapping("/category/{id}")
    public CategoryResponse getCategory(@PathVariable Long id) {
        var category = categoryService.findById(id);
        if (category.isEmpty())
            throw new IllegalArgumentException("The category doesn't exist");
        return CategoryResponse.fromCategory(category.get());
    }

    @PostMapping("/category")
    public ResponseEntity<SuccessDTO> saveOperation(@RequestBody CategoryRequest category, HttpServletRequest request) {
        var session = request.getSession(false);
        var userId = (long) session.getAttribute(Constants.SESSION_USER_ID);
        var user = this.userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        categoryService.save(category, user);
        return ResponseEntity.ok().body(SuccessDTO.builder().message("Category created successfully").build());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleNotFound(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
