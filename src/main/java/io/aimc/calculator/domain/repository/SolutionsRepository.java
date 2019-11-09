package io.aimc.calculator.domain.repository;

import io.aimc.calculator.domain.entity.SolutionHistoryElement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий предназначен для хранения/выборки математических уравнений и их решений
 */
@Repository
public interface SolutionsRepository extends CrudRepository<SolutionHistoryElement, Long> {

}
