package io.aimc.calculator.rest;

import io.aimc.calculator.services.ICalculator;
import io.aimc.calculator.services.impl.Calculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest({ICalculator.class, CalculatorRest.class})
class CalculatorRestTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Calculator calculator;

    @Test
    void calculate() throws Exception {
        String expression = "5+5";
        String solution = "10";

        when(calculator.solution(expression)).thenReturn(solution);

        mvc.perform(MockMvcRequestBuilders
                .post("/api/calculator/solve/" + expression)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("solution")
                        .value(solution));
    }
}