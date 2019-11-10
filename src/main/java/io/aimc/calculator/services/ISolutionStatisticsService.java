package io.aimc.calculator.services;

import io.aimc.calculator.domain.entity.SolutionHistoryElement;

import java.util.Date;
import java.util.List;

/**
 * Интерфейс предназначен для реализации статистики математических решений.
 */
public interface ISolutionStatisticsService {
    /**
     * Считает количество решений по дате.
     *
     * @param date - дата по которой осуществляется выборка.
     * @return - количество найденных записей.
     */
    int getCountSolutionsByData(Date date);

    /**
     * Считает количество решений по знаку операции.
     *
     * @param operation - символ операции.
     * @return - количество найденных записей.
     */
    int getCountSolutionsByOperation(char operation);

    /**
     * Возвращает лист найденных записей по дате.
     *
     * @param date - дата по которой осуществляется выборка.
     * @return - лист с найденными записями.
     */
    List<SolutionHistoryElement> getSolutionsByData(Date date);

    /**
     * Возвращает лист найденных записей по знаку операции.
     *
     * @param operation - символ операции.
     * @return - лист с найденными записями.
     */
    List<SolutionHistoryElement> getSolutionsByOperation(char operation);

    /**
     * Находит самое часто используемое число в уравнениях.
     *
     * @return - популярное число.
     */
    double getPopularNumber();
}
