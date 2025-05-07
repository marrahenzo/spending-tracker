package io.github.marrahenzo.spending_tracker.repository;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.JoinType;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import io.github.marrahenzo.spending_tracker.model.Operation;
import io.github.marrahenzo.spending_tracker.model.entityview.AccountBalanceView;
import io.github.marrahenzo.spending_tracker.model.entityview.AmountPerCategoryView;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OperationCustomRepositoryImpl implements OperationCustomRepository {

    private CriteriaBuilderFactory builderFactory;
    private EntityManager entityManager;
    private EntityViewManager entityViewManager;

    @Autowired
    public OperationCustomRepositoryImpl(CriteriaBuilderFactory builderFactory,
                                         EntityManager entityManager,
                                         EntityViewManager entityViewManager) {
        this.builderFactory = builderFactory;
        this.entityManager = entityManager;
        this.entityViewManager = entityViewManager;
    }

    public BigDecimal getBalance(Long userId) {
        var setting = EntityViewSetting.create(AccountBalanceView.class);

        var result = entityViewManager.applySetting(
                setting,
                this.builderFactory
                        .create(this.entityManager, Operation.class, "o")
                        .select("SUM(o.amount)")
                        .where("o.user.id").eq(userId)

        ).getSingleResult();

        return result.getAmount();
    }

    public List<AmountPerCategoryView> getAmountPerCategory(Long userId) {
        var setting = EntityViewSetting.create(AmountPerCategoryView.class);

        return entityViewManager.applySetting(
                setting,
                this.builderFactory
                        .create(this.entityManager, Operation.class, "operation")
                        .join("operation.category", "category", JoinType.LEFT)
                        .where("operation.user.id").eq(userId)
                        .groupBy("category.id", "category.name")

        ).getResultList();
    }
}
