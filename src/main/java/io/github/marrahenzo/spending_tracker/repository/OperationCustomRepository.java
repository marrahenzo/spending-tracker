package io.github.marrahenzo.spending_tracker.repository;

import io.github.marrahenzo.spending_tracker.model.entityview.AmountPerCategoryView;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OperationCustomRepository {

    public BigDecimal getBalance(Long userId);

    public List<AmountPerCategoryView> getAmountPerCategory(Long userId);
}
