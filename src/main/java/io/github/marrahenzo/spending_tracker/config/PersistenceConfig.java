package io.github.marrahenzo.spending_tracker.config;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViews;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import io.github.marrahenzo.spending_tracker.model.entityview.AccountBalanceView;
import io.github.marrahenzo.spending_tracker.model.entityview.AmountPerCategoryView;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public CriteriaBuilderFactory createCriteriaBuilderFactory() {
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        return config.createCriteriaBuilderFactory(entityManagerFactory);
    }

    @Bean
    public EntityViewConfiguration entityViewConfiguration() {
        EntityViewConfiguration config = EntityViews.createDefaultConfiguration();

        config.addEntityView(AccountBalanceView.class);
        config.addEntityView(AmountPerCategoryView.class);

        return config;
    }

    @Bean
    public EntityViewManager createEntityViewManager(
            CriteriaBuilderFactory criteriaBuilderFactory, EntityViewConfiguration entityViewConfiguration) {
        return entityViewConfiguration.createEntityViewManager(criteriaBuilderFactory);
    }
}
