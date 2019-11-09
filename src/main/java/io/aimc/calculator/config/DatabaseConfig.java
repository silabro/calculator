package io.aimc.calculator.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("io.aimc.calculator.domain.entity")
@EnableJpaRepositories(basePackages = {"io.aimc.calculator.domain.repository"})
public class DatabaseConfig {

}
