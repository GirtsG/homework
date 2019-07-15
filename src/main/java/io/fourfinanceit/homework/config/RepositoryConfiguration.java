package io.fourfinanceit.homework.config;

import io.fourfinanceit.homework.entity.LoanApplication;
import io.fourfinanceit.homework.entity.LoanExtension;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
@EnableJpaAuditing
public class RepositoryConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(LoanApplication.class, LoanExtension.class);
    }
}
