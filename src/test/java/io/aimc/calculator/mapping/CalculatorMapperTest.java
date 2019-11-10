package io.aimc.calculator.mapping;

import io.aimc.calculator.dto.CalculatorResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorMapperTest {

    @Test
    void stringWithSolutionToCalculatorResponseDTO() {
        String expectedSolution = "100500";

        CalculatorResponseDTO result = CalculatorMapper.MAPPER.solutionToCalculatorResponseDTO(expectedSolution);

        assertEquals(expectedSolution, result.getSolution());
    }
}