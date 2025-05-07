package io.github.marrahenzo.spending_tracker.model.entityview;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;
import io.github.marrahenzo.spending_tracker.model.Operation;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Immutable
@EntityView(Operation.class)
public interface AccountBalanceView {
    @Mapping("SUM(amount)")
    BigDecimal getAmount();
}
