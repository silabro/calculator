package io.aimc.calculator.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CalculatorTest {

    @InjectMocks
    private Calculator calculator;

    @Test
    void simpleExpressionInBracket() {
        String expression = "(-5+5)";
        String expectedResult = "0.0";

        String result = calculator.solution(expression);

        assertEquals(expectedResult, result);
    }

    @Test
    void advancedExpressionWithBrackets() {
        String expression = "(-7*8+9-(9/4.5))^2";
        String expectedResult = "2401.0";

        String result = calculator.solution(expression);

        assertEquals(expectedResult, result);
    }

    @Test
    void advancedExpressionWithoutBrackets() {
        String expression = "9*1+4.5";
        String expectedResult = "13.5";

        String result = calculator.solution(expression);

        assertEquals(expectedResult, result);
    }
}