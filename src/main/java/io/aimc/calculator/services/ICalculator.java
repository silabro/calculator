package io.aimc.calculator.services;

public interface ICalculator {
    /**
     * Решает математическое уравнение.
     *
     * @param fullExpression - Строка уравнения
     * @return - Строка решения
     */
    String solution(String fullExpression);
}
